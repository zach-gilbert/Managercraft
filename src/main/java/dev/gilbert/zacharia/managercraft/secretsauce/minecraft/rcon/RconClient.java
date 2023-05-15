package dev.gilbert.zacharia.managercraft.secretsauce.minecraft.rcon;

import lombok.extern.slf4j.Slf4j;
import nl.vv32.rcon.Rcon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Component for initializing a client to send RCON requests to the
 * minecraft server.
 */
@Slf4j
@Component
public class RconClient {

    //todo: finish javadocs

    private final String host;
    private final int port;
    private final String password;
    private Rcon rcon;

    public RconClient(@Value("${minecraft.rcon.host}") String host,
                      @Value("${minecraft.rcon.port}") int port,
                      @Value("${minecraft.rcon.password}") String password) {
        this.host = host;
        this.port = port;
        this.password = password;
    }

    public void initialize() {
        try {
            rcon = Rcon.open(this.host, this.port);

            if (rcon.authenticate(this.password)) {
                log.info("Rcon Client Authenticated Successfully");
            }

            else {
                log.error("Failed to authenticate Rcon Client");
            }
        }

        catch (IOException e) {
            log.error("Failed to initialize Rcon Client", e);
            throw new RuntimeException(e);
        }
    }

    public void teardown() {
        try {
            if (rcon != null) {
                log.info("Closing Rcon client");
                rcon.close();
            }
        }

        catch (IOException e) {
            log.error("Error attempting to close Rcon client");
            throw new RuntimeException(e);
        }
    }

    public void sendCommand(String command) {

        if (rcon != null) {
            try {
                log.info("Sending Rcon request: {}", command);
                rcon.sendCommand(command);
            }

            catch (IOException e) {
                log.error("Failed to send command: " + command, e);
            }
        }

        else {
            log.error("Rcon not initialized. Please call initialize() first.");
        }
    }

    public boolean sendCommand(String command, String parameter) {
        String fullCommand = command;
        boolean success = false;

        if (parameter != null && !parameter.isEmpty()) {
            fullCommand += " " + parameter;
        }

        if (rcon != null) {
            try {
                log.info("Sending Rcon request: {}", fullCommand);
                String response = rcon.sendCommand(fullCommand);

                if (!response.contains("Unknown or incomplete command")) {
                    success = true;
                }
            }

            catch (IOException e) {
                log.error("Failed to send command: " + fullCommand, e);
            }
        }

        else {
            log.error("Rcon not initialized. Please call initialize() first.");
        }

        return success;
    }

}
