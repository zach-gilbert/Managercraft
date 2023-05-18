package dev.gilbert.zacharia.managercraft;

import dev.gilbert.zacharia.managercraft.configuration.ShutdownListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ManagercraftApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagercraftApplication.class, args);
    }

    @Bean
    public ShutdownListener shutdownListener() {
        return new ShutdownListener();
    }

}
