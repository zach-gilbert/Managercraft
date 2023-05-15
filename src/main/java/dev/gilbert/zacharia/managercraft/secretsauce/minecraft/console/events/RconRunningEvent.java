package dev.gilbert.zacharia.managercraft.secretsauce.minecraft.console.events;

import org.springframework.context.ApplicationEvent;

public class RconRunningEvent extends ApplicationEvent {

    public RconRunningEvent(String message) {
        super(message);
    }

    public String getMessage() {
        return (String) getSource();
    }
}
