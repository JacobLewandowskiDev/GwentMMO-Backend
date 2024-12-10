package com.jakub_lewandowski.gwent_backend.api;

import com.jakub_lewandowski.gwent_backend.model.MovementUpdate;
import com.jakub_lewandowski.gwent_backend.model.PlayerDisconnectMessage;
import com.jakub_lewandowski.gwent_backend.model.PlayerWebSocketHandler;
import com.jakub_lewandowski.gwent_backend.service.PlayerService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
public class PlayerWebSocketController {

    private final PlayerService playerService;
    private final SimpMessageSendingOperations messagingTemplate;

    public PlayerWebSocketController(PlayerService playerService, SimpMessageSendingOperations messagingTemplate) {
        this.playerService = playerService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/move")
    @SendTo("/topic/movement")
    public MovementUpdate broadcastMovement(MovementUpdate movementUpdate) {
        return movementUpdate;
    }

    @MessageMapping("/player-disconnect")
    public void handlePlayerDisconnect(@Payload PlayerDisconnectMessage message) {
        try {
            long playerId = Long.parseLong(message.getId()); // Convert String to long
            System.out.println("Player disconnected: " + playerId);

            PlayerWebSocketHandler.closeConnection(playerId);
            playerService.deletePlayer(playerId);
        } catch (NumberFormatException e) {
            System.err.println("Invalid player ID format: " + message.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
