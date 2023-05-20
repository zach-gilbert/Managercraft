package dev.gilbert.zacharia.managercraft.secretsauce.minecraft.property;

import dev.gilbert.zacharia.managercraft.models.requests.MinecraftStartServerRequest;
import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.server.ServerPropertyKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.security.SecureRandom;

@Component
public class PropertyUtility {

    private final PropertyService propertyService;
    private final String rootServerDir;

    @Autowired
    PropertyUtility(PropertyService propertyService,
                    @Value("${minecraft.servers.directory}") String rootServerDir) {
        this.propertyService = propertyService;
        this.rootServerDir = rootServerDir;
    }

    public void verifyServerProperties(MinecraftStartServerRequest request) {
        // Initialize property service
        propertyService.initializeService(Paths.get(rootServerDir, request.getServer(), "server.properties"));

        // Verify the RCON parameters
        enableRcon();
        verifyRconPort();
        verifyRconPassword();
    }

    private void enableRcon() {
        boolean isRconEnabled = Boolean.parseBoolean(
                propertyService.getProperty(ServerPropertyKeys.RCON_ENABLED));

        if (!isRconEnabled) {
            propertyService.setProperty(ServerPropertyKeys.RCON_ENABLED, "true");
        }
    }

    private void verifyRconPort() {
        String rconPort = propertyService.getProperty(ServerPropertyKeys.RCON_PORT);

        if (rconPort == null || rconPort.isEmpty()) {
            propertyService.setProperty(ServerPropertyKeys.RCON_PORT, "25575");
        }
    }

    private void verifyRconPassword() {
        String rconPassword = propertyService.getProperty(ServerPropertyKeys.RCON_PASSWORD);

        if (rconPassword == null || rconPassword.isEmpty()) {
            propertyService.setProperty(ServerPropertyKeys.RCON_PASSWORD, generatePassword());
        }
    }

    private String generatePassword() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int PASSWORD_LENGTH = 10;

        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }
}
