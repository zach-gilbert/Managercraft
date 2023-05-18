package dev.gilbert.zacharia.managercraft.secretsauce.minecraft;

import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.console.ConsoleReaderService;
import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.rcon.RconHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MinecraftServerManager {

    private final RconHandler rconHandler;
    private final ConsoleReaderService consoleReaderService;
    private final String serverDirectory;
    private Process process;

    @Autowired
    public MinecraftServerManager(RconHandler rconHandler,
                                  ConsoleReaderService consoleReaderService,
                                  @Value("${minecraft.servers.directory}") String serverDirectory) {
        this.rconHandler = rconHandler;
        this.consoleReaderService = consoleReaderService;
        this.serverDirectory = serverDirectory;
    }

    /* TODO:
        - Enhance manager to accept parameter of a minecraft.jar instance as well as the parameters assosciated with
        starting the server. This will allow starting/stopping separate minecraft servers depending on parameter passed
     */

    //todo: change to return a generic response message
    public void startServer() {
        //todo: add logic to check if it is already started, if so send stop request, then start it again.

        File jarFile = new File(serverDirectory + "/Academy/forge-1.16.5-36.2.34.jar");//todo add logic to find .jar to execute, and also let user decide

        List<String> command = getCommandArgs();
        log.info("Starting Minecraft Server with arguments: {}", command);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.directory(jarFile.getParentFile());
        processBuilder.redirectErrorStream(true);

        try {
            process = processBuilder.start();
            consoleReaderService.readConsole(process);
        }

        catch (IOException e) {
            log.error("Exception trying to start Minecraft Server: ", e);
            throw new RuntimeException(e);
        }

    }

    // todo: Change to return a generic response message
    public void stopServer() {
        log.info("Attempting to Stop Minecraft Server");

        if (process != null && process.isAlive()) {
            rconHandler.saveAll();
            log.info("Stopping Minecraft Server");
            process.destroy();
        }

        else {
            log.warn("Minecraft Server already stopped");
        }

        rconHandler.tearDown();
    }

    // TODO: Better define this
    private List<String> getCommandArgs() {
        List<String> command = new ArrayList<>();
        command.add("C:\\Users\\Zach\\Desktop\\servers\\Academy\\jre\\jdk8u312-b07-jre\\bin\\java.exe");//todo improve this from a properties file
        command.add("-server");
        command.add("-javaagent:log4jfix/Log4jPatcher-1.0.0.jar");
        command.add("-XX:+UseG1GC");
        command.add("-XX:+UnlockExperimentalVMOptions");
        command.add("-Xmx6144M");
        command.add("-Xms4096M");
        command.add("-jar");
        command.add("forge-1.16.5-36.2.34.jar");
        command.add("nogui");

        return command;
    }

}
