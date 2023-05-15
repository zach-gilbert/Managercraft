package dev.gilbert.zacharia.managercraft.secretsauce.minecraft.console.listeners;

import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.console.events.SaveAllEvent;
import dev.gilbert.zacharia.managercraft.secretsauce.minecraft.rcon.RconHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SaveAllEventListener {

    private final RconHandler rconHandler;

    @Autowired
    public SaveAllEventListener(RconHandler rconHandler) {
        this.rconHandler = rconHandler;
    }

    @EventListener
    public void onSaveAllEvent(SaveAllEvent event) {
        rconHandler.completeFuture(event.getMessage());
    }
}
