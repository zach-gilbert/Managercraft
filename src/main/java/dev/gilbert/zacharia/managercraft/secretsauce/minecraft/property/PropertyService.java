package dev.gilbert.zacharia.managercraft.secretsauce.minecraft.property;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

//todo: javadoc code

/**
 * Service to retrieve and set properties within a file
 */
@Slf4j
@Service
public class PropertyService {
    private final Properties properties;
    private Path propertiesPath;

    public PropertyService() {
        this.properties = new Properties();
    }

    public void initializeService(Path propertiesPath) {
        this.propertiesPath = propertiesPath;
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream in = Files.newInputStream(propertiesPath)) {
            properties.load(in);
        }

        catch (IOException e) {
            log.error("Failed to load properties file: {}\n{}", propertiesPath, e);
            throw new RuntimeException("Failed to load properties file: " + propertiesPath, e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        storeProperties();
    }

    private void storeProperties() {
        try (OutputStream out = Files.newOutputStream(propertiesPath)) {
            properties.store(out, null);
        }

        catch (IOException e) {
            throw new RuntimeException("Failed to store properties file: " + propertiesPath, e);
        }
    }
}
