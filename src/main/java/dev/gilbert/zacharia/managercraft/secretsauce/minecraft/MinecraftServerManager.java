package dev.gilbert.zacharia.managercraft.secretsauce.minecraft;

import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.console.ConsoleReaderService;
import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.rcon.RconHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public MinecraftServerManager(RconHandler rconHandler,
                                  ConsoleReaderService consoleReaderService) {
        this.rconHandler = rconHandler;
        this.consoleReaderService = consoleReaderService;
    }

    private final String SERVER_DIRECTORY = "C:/Users/Zach/Desktop/servers";

    private Process process;

    public Process getProcess() {
        return process;
    }

    /* TODO:
        - Enhance manager to accept parameter of a minecraft.jar instance as well as the parameters assosciated with
        starting the server. This will allow starting/stopping separate minecraft servers depending on parameter passed
     */

    //todo: change to return a generic response message
    public void startServer() {
        //todo: add logic to check if it is already started, if so send stop request, then start it again.

        File jarFile = new File(SERVER_DIRECTORY + "/Academy/forge-1.16.5-36.2.34.jar");

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

    // TODO: Better define this, prob shouldn't be a method
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
        command.add("forge-1.16.5-36.2.34.jar");//todo: investigate if this can be removed
        command.add("nogui");

        return command;
    }

}
