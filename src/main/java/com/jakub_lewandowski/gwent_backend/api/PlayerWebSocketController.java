package com.jakub_lewandowski.gwent_backend.api;

import com.jakub_lewandowski.gwent_backend.model.MovementUpdate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class PlayerWebSocketController {

    @MessageMapping("/move")
    @SendTo("/topic/movement")
    public MovementUpdate broadcastMovement(MovementUpdate movementUpdate) {
        return movementUpdate;
    }
}
