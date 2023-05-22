package dev.gilbert.zacharia.managercraft.configuration;

import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.server.ServerProcessManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

public class ShutdownListener implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    private ServerProcessManager serverProcessManager;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        serverProcessManager.stopServer();
    }
}
