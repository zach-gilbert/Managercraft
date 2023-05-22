package dev.gilbert.zacharia.managercraft.secretsauce.minecraft.server;

import dev.gilbert.zacharia.managercraft.models.requests.MinecraftStartServerRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ServerFileManager {

    private final String rootServersDir;

    @Autowired
    public ServerFileManager(@Value("${minecraft.servers.directory}") String rootServersDir) {
        this.rootServersDir = rootServersDir;
    }

    public List<String> getLaunchServerArgs(MinecraftStartServerRequest request, String launchJarName) {
        List<String> customArgs = new ArrayList<>();

        // If a no path to JRE is provided, use JAVA HOME
        if (request.getJavaRuntimeEnv() == null ||
                request.getJavaRuntimeEnv().isEmpty()) {

            Path javaHome = Paths.get(System.getProperty("java.home"));
            Path javaExecutable = javaHome.resolve("bin").resolve("java.exe");

            customArgs.add(javaExecutable.toString());
        }
        // Else if a JRE is provided, use it
        else {
            Path javaExecutablePath = Paths.get(request.getJavaRuntimeEnv()).resolve("bin/java.exe");
            customArgs.add(javaExecutablePath.toString());
        }

        // Setting arguments
        customArgs.add("-server");
        customArgs.add("-XX:+UseG1GC");
        customArgs.add("-XX:+UnlockExperimentalVMOptions"); //todo: figure out if this may cause issues
        customArgs.add("-Xmx" + request.getMaximumMemory() + "M"); // -Xmx1024M
        customArgs.add("-Xms" + request.getMinimumMemory() + "M"); // -Xms1024M
        customArgs.add("-jar");
        customArgs.add(launchJarName); // Server launch jar
        customArgs.add("nogui");

        if (request.getAdditionalServerArgs() != null &&
                !request.getAdditionalServerArgs().isEmpty()) {
            // Incorporate additional args into the options
            customArgs.addAll(request.getAdditionalServerArgs());
        }

        return customArgs;
    }

    private List<String> findAllJarsInDirectory(String rootServersDir, String server) {
        List<String> jarsInDirectory = new ArrayList<>();

        Path serverPath = Paths.get(rootServersDir, server);
        File serverDir = serverPath.toFile();

        // Get list of files within the directory
        File[] files = serverDir.listFiles();

        // Guard clause
        if (files == null) {
            log.error("Error reading directory: " + serverPath);
            return jarsInDirectory;
        }

        // Iterate through list of files for jars
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".jar")) {
                jarsInDirectory.add(file.getName());
            }
        }

        return jarsInDirectory;
    }

    private String findServerLaunchJarName(String rootServersDir, String server) {
        List<String> jarsInDirectory = findAllJarsInDirectory(rootServersDir, server);

        // Guard clause
        if (jarsInDirectory.isEmpty()) {
            log.error("Error identifying launch jar");
            return "";
        }

        // Iterate and search for a forge jar, in case someone is using a modpack
        for (String jar : jarsInDirectory) {
            if (jar.contains("forge")) {
                return jar;
            }
        }

        // Assuming no jar containing forge is found, return default for vanilla
        return jarsInDirectory.get(0);
    }

    public String getLaunchJarName(MinecraftStartServerRequest request) {
        String launchJarName; // Retrieve the launch jar for the specific server directory

        // If no launch jar is provided, automatically retrieve it
        if (request.getLaunchJarName() == null || request.getLaunchJarName().isEmpty()) {
            launchJarName = findServerLaunchJarName(rootServersDir, request.getServer());
        }
        // Else, use the provided launch jar
        else {
            launchJarName = request.getLaunchJarName();
        }

        return launchJarName;
    }
}
