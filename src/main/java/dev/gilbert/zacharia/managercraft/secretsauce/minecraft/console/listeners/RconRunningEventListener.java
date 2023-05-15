package dev.gilbert.zacharia.managercraft.secretsauce.minecraft.console.listeners;

import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.console.events.RconRunningEvent;
import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.rcon.RconHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RconRunningEventListener {

    private final RconHandler rconHandler;

    @Autowired
    public RconRunningEventListener(RconHandler rconHandler) {
        this.rconHandler = rconHandler;
    }

    @EventListener
    public void onRconRunningEvent(RconRunningEvent event) {
        rconHandler.initialize();
    }
}
