package dev.gilbert.zacharia.managercraft.secretsauce.minecraft.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * A service for sending WebSocket messages to the UI
 */
@Service
public class WebSocketSender {

    private final SimpMessagingTemplate simpleMessagingTemplate;

    @Autowired
    public WebSocketSender(SimpMessagingTemplate messagingTemplate) {
        this.simpleMessagingTemplate = messagingTemplate;
    }

    public void sendConsoleMessage(String destinationTopic, String consoleLine) {

        if (destinationTopic.equals(WebSocketTopics.MANAGERCRAFT_CONSOLE)) {
            simpleMessagingTemplate.convertAndSend(destinationTopic, prepareManagercraftMessage() + consoleLine);
        }

        else {
            simpleMessagingTemplate.convertAndSend(destinationTopic, consoleLine);
        }
    }

    private String prepareManagercraftMessage() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        return"[" + formattedTime + "] [Managercraft]: ";
    }

}
