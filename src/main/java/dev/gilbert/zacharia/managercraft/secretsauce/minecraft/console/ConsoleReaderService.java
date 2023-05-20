package dev.gilbert.zacharia.managercraft.secretsauce.minecraft.console;

import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.console.events.RconRunningEvent;
import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.console.events.SaveAllEvent;
import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.property.PropertyService;
import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.server.ServerPropertyKeys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Service for reading the console output of a process asynchronously
 */
@Slf4j
@Service
public class ConsoleReaderService {

    private final ApplicationEventPublisher eventPublisher;
    private final TaskExecutor taskExecutor;
    private final PropertyService propertyService;

    /**
     * Autowire constructor for the ConsoleReaderService.
     *
     * @param eventPublisher    EventPublisher used for emitting events
     * @param taskExecutor      TaskExecutor used for executing threads asynchronously
     */
    @Autowired
    public ConsoleReaderService(ApplicationEventPublisher eventPublisher,
                                @Qualifier("applicationTaskExecutor") TaskExecutor taskExecutor,
                                PropertyService propertyService) {
        this.eventPublisher = eventPublisher;
        this.taskExecutor = taskExecutor;
        this.propertyService = propertyService;
    }

    /**
     * Reads the console of the given process asynchronously.
     * The console is handled in a separate thread, allowing for concurrent processing.
     *
     * @param process   Process that's console output will be read
     */
    @Async
    public void readConsole(Process process) {
        taskExecutor.execute(() -> {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {

                String consoleLine;
                while ((consoleLine = reader.readLine()) != null) {
                    log.info(consoleLine);

                    String rconPort = propertyService.getProperty(
                            ServerPropertyKeys.RCON_PORT);

                    if (consoleLine.contains("RCON running on 0.0.0.0:" + rconPort)) {
                        eventPublisher.publishEvent(new RconRunningEvent(consoleLine));
                    }

                    if (consoleLine.contains("[Rcon: Saved the game]")) {
                        eventPublisher.publishEvent(new SaveAllEvent(consoleLine));
                    }
                }
            } catch (IOException e) {
                log.error("Error reading console: ", e);
            }
        });
    }

}
