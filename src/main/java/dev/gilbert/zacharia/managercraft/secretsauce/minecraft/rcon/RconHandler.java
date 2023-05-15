package dev.gilbert.zacharia.managercraft.secretsauce.minecraft.rcon;

import dev.gilbert.zacharia.managercraft.models.responses.GenericResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


/**
 * This Component is designed to use the Rcon Client to send requests
 */
@Slf4j
@Component
public class RconHandler {

    private final CompletableFuture<String> future = new CompletableFuture<>();

    private final RconClient rconClient;

    @Autowired
    public RconHandler(RconClient rconClient) {
        this.rconClient = rconClient;
    }

    public void completeFuture(String message) {
        future.complete(message);
    }

    public void initialize() {
        rconClient.initialize();
    }

    public void tearDown() {
        rconClient.teardown();
    }

    public GenericResponseMessage saveAll() {
        GenericResponseMessage genericResponseMessage = new GenericResponseMessage();

        String response = "";
        boolean success = false;

        if (rconClient != null) {
            rconClient.sendCommand(RconCommand.SAVE_ALL.getCommand());

            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

            // Timeout Task
            final Runnable timeout = () -> {
                future.complete("Error: Timeout reached");
            };

            // Schedule Timeout
            final ScheduledFuture<?> timeoutTask = scheduledExecutorService.schedule(timeout, 15, TimeUnit.SECONDS);

            try {
                // Await the result or timeout
                response = future.get();

                timeoutTask.cancel(true);  // Cancel the timeout task if future completed normally

                // Check the response and set the success status
                if (!response.equals("Error: Timeout reached")) {
                    success = true;
                }
            }

            catch (InterruptedException | ExecutionException e) {
                log.error("Error waiting for console: ", e);
            }

            scheduledExecutorService.shutdown();
        }

        genericResponseMessage.setSuccess(success);
        genericResponseMessage.setMessage(response);

        return genericResponseMessage;
    }

    public GenericResponseMessage say(String message) {
        GenericResponseMessage genericResponseMessage = new GenericResponseMessage();

        String response;
        boolean success;


        success = rconClient.sendCommand(RconCommand.SAY.getCommand(), message);

        if (success) {
            response = "Message successfully sent";
        } else {
            response = "Error: Could not send command: " + RconCommand.SAY.getCommand();
        }

        genericResponseMessage.setMessage(response);
        genericResponseMessage.setSuccess(success);

        return genericResponseMessage;
    }

}
