package dev.gilbert.zacharia.managercraft.secretsauce.minecraft.server;

import dev.gilbert.zacharia.managercraft.models.requests.MinecraftStartServerRequest;
import dev.gilbert.zacharia.managercraft.models.responses.GenericResponseMessage;
import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.console.ConsoleReaderService;
import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.property.PropertyUtility;
import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.rcon.RconHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

//todo: add java docs to this class, at least the main class individual methods will likely be updated so wait on adding those javadocs

/**
 * Service for managing a Minecraft Server.
 * Only one instance of a server may be operational at a time.
 */
@Slf4j
@Service
public class ServerProcessManager {

    private final RconHandler rconHandler;
    private final ConsoleReaderService consoleReaderService;
    private final PropertyUtility propertyUtility;
    private final ServerFileManager serverFileManager;

    private MinecraftStartServerRequest request;
    private final String rootServersDir;
    private Process process;

    /**
     * 'scheduler' is a thread pool executor that can schedule commands to run after a delay.
     * It contains 1 thread to execute, and will execute the restartServer method
     */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    /**
     * Handler for controlling a scheduled future that is produced by the above 'scheduler'
     * It is a reference to the future result of restartServer() task.
     * Task is cancelled if 'stopServer()' method is called
     */
    private ScheduledFuture<?> restartFuture;

    @Autowired
    public ServerProcessManager(RconHandler rconHandler,
                                ConsoleReaderService consoleReaderService,
                                PropertyUtility propertyUtility,
                                @Value("${minecraft.servers.directory}") String rootServersDir,
                                ServerFileManager serverFileManager) {
        this.rconHandler = rconHandler;
        this.consoleReaderService = consoleReaderService;
        this.propertyUtility = propertyUtility;
        this.rootServersDir = rootServersDir;
        this.serverFileManager = serverFileManager;
    }

    public GenericResponseMessage startServer(MinecraftStartServerRequest request) {
        GenericResponseMessage genericResponseMessage = new GenericResponseMessage();

        this.request = request; // Store the request for restarting

        // Stop the server if it is already running, then proceed and start it again
        stopServer();

        propertyUtility.verifyServerProperties(request);

        // Retrieve the launch jar for the specific server directory
        String launchJarName = serverFileManager.getLaunchJarName(request);

        // Guard clause in case the launch jar is not found
        if (launchJarName == null || launchJarName.isEmpty()) {
            log.error("Launch jar for server, '{}', not found", request.getServer());
            genericResponseMessage.setMessage("Launch jar for server, " + request.getServer() + ", not found");
            genericResponseMessage.setSuccess(false);

            return genericResponseMessage;
        }

        // Build the path and File objects given the server directory
        Path jarPath = Paths.get(rootServersDir, request.getServer(), launchJarName);
        File jarFile = jarPath.toFile();

        List<String> serverArgs = serverFileManager.getLaunchServerArgs(request, launchJarName);
        log.info("Starting Minecraft Server with arguments: {}", serverArgs);

        // Start the server
        try {
            startProcess(jarFile, serverArgs);
            genericResponseMessage.setSuccess(true);
            genericResponseMessage.setMessage(request.getServer() + " server successfully started");

            // Start the timer to restart server if auto-restart is requested
            if (request.isAutoRestart()) {
                restartFuture = scheduler.schedule(this::restartServer, request.getAutoRestartInterval(), TimeUnit.HOURS);
            }
        }

        catch (IOException e) {
            log.error("Exception trying to start Minecraft Server: ", e);
            genericResponseMessage.setSuccess(false);
            genericResponseMessage.setMessage("Unable to start " + request.getServer());
            throw new RuntimeException(e);// TODO: throw ServerStartException
        }

        return genericResponseMessage;
    }

    // todo: Change to return a generic response message
    public void stopServer() {
        log.info("Attempting to Stop Minecraft Server");

        if (process != null && process.isAlive()) {
            rconHandler.saveAll();
            log.info("Stopping Minecraft Server");
            process.destroy();
            process = null;
        }

        else {
            log.warn("Minecraft Server already stopped");
        }

        rconHandler.tearDown();

        // Cancel the restart task if it's scheduled
        if (restartFuture != null) {
            restartFuture.cancel(false);
            restartFuture = null;
        }
    }

    private void restartServer() {
        log.info("Restarting the server: {}", request.getServer());
        //todo: add warning logic for restart to announce to players
        //todo, add a flag in request if the server should restart or not
        startServer(request);
    }

    private void startProcess(File jarFile, List<String> serverArgs) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(serverArgs);
        processBuilder.directory(jarFile.getParentFile());
        processBuilder.redirectErrorStream(true);

        process = processBuilder.start();
        consoleReaderService.readConsole(process);
    }

//    private List<String> getLaunchServerArgs(MinecraftStartServerRequest request, String launchJarName) {
//        List<String> customArgs = new ArrayList<>();
//
//        // If a no path to JRE is provided, use JAVA HOME
//        if (request.getJavaRuntimeEnv() == null ||
//                request.getJavaRuntimeEnv().isEmpty()) {
//
//            Path javaHome = Paths.get(System.getProperty("java.home"));
//            Path javaExecutable = javaHome.resolve("bin").resolve("java.exe");
//
//            customArgs.add(javaExecutable.toString());
//        }
//        // Else if a JRE is provided, use it
//        else {
//            Path javaExecutablePath = Paths.get(request.getJavaRuntimeEnv()).resolve("bin/java.exe");
//            customArgs.add(javaExecutablePath.toString());
//        }
//
//        // Setting arguments
//        customArgs.add("-server");
//        customArgs.add("-XX:+UseG1GC");
//        customArgs.add("-XX:+UnlockExperimentalVMOptions"); //todo: figure out if this may cause issues
//        customArgs.add("-Xmx" + request.getMaximumMemory() + "M"); // -Xmx1024M
//        customArgs.add("-Xms" + request.getMinimumMemory() + "M"); // -Xms1024M
//        customArgs.add("-jar");
//        customArgs.add(launchJarName); // Server launch jar
//        customArgs.add("nogui");
//
//        if (request.getAdditionalServerArgs() != null &&
//                !request.getAdditionalServerArgs().isEmpty()) {
//            // Incorporate additional args into the options
//            customArgs.addAll(request.getAdditionalServerArgs());
//        }
//
//        return customArgs;
//    }

//    private List<String> findAllJarsInDirectory(String rootServersDir, String server) {
//        List<String> jarsInDirectory = new ArrayList<>();
//
//        Path serverPath = Paths.get(rootServersDir, server);
//        File serverDir = serverPath.toFile();
//
//        // Get list of files within the directory
//        File[] files = serverDir.listFiles();
//
//        // Guard clause
//        if (files == null) {
//            log.error("Error reading directory: " + serverPath);
//            return jarsInDirectory;
//        }
//
//        // Iterate through list of files for jars
//        for (File file : files) {
//            if (file.isFile() && file.getName().endsWith(".jar")) {
//                jarsInDirectory.add(file.getName());
//            }
//        }
//
//        return jarsInDirectory;
//    }
//
//    private String findServerLaunchJarName(String rootServersDir, String server) {
//        List<String> jarsInDirectory = findAllJarsInDirectory(rootServersDir, server);
//
//        // Guard clause
//        if (jarsInDirectory.isEmpty()) {
//            log.error("Error identifying launch jar");
//            return "";
//        }
//
//        // Iterate and search for a forge jar, in case someone is using a modpack
//        for (String jar : jarsInDirectory) {
//            if (jar.contains("forge")) {
//                return jar;
//            }
//        }
//
//        // Assuming no jar containing forge is found, return default for vanilla
//        return jarsInDirectory.get(0);
//    }
//
//    private String getLaunchJarName(MinecraftStartServerRequest request) {
//        String launchJarName; // Retrieve the launch jar for the specific server directory
//
//        // If no launch jar is provided, automatically retrieve it
//        if (request.getLaunchJarName() == null || request.getLaunchJarName().isEmpty()) {
//            launchJarName = findServerLaunchJarName(rootServersDir, request.getServer());
//        }
//        // Else, use the provided launch jar
//        else {
//            launchJarName = request.getLaunchJarName();
//        }
//
//        return launchJarName;
//    }

}
