package dev.gilbert.zacharia.managercraft.secretsauce.minecraft.console.events;

import org.springframework.context.ApplicationEvent;

public class SaveAllEvent extends ApplicationEvent {

    public SaveAllEvent(String message) {
        super(message);
    }

    public String getMessage() {
        return (String) getSource();
    }
}

