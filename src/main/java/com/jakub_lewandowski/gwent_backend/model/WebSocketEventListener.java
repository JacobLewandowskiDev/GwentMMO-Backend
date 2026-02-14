package com.jakub_lewandowski.gwent_backend.model;

import com.jakub_lewandowski.gwent_backend.service.PlayerService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener {

    private static final Map<String, String> activeSessions = new ConcurrentHashMap<>();
    private static final int MAX_CONNECTIONS = 10;
    private final PlayerService playerService;
    private final SimpMessageSendingOperations messagingTemplate;

    public WebSocketEventListener(PlayerService playerService, SimpMessageSendingOperations messagingTemplate) {
        this.playerService = playerService;
        this.messagingTemplate = messagingTemplate;
    }


    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String playerId = headerAccessor.getFirstNativeHeader("playerId");

        if (playerId != null && activeSessions.size() < MAX_CONNECTIONS) {
            activeSessions.put(sessionId, playerId);
            System.out.println("Player with ID: [" + playerId + "] has joined the server. Server status: [" + activeSessions.size() + "/" + MAX_CONNECTIONS + "].");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        String playerIdStr = activeSessions.remove(sessionId);

        if (playerIdStr != null) {
            try {
                long playerId = Long.parseLong(playerIdStr);

                playerService.deletePlayer(playerId);

                messagingTemplate.convertAndSend("/topic/player-disconnect", Map.of(
                        "action", "disconnect",
                        "playerId", playerId
                ));

                System.out.println("Player: [" + playerId + "] has disconnected. Current connections: [" + activeSessions.size() + "/" + MAX_CONNECTIONS + "].");
            } catch (Exception e) {
                System.err.println("Error removing player: " + playerIdStr + ": " + e.getMessage());
            }
        }
    }

    public static boolean currentPlayerCount() {
        return activeSessions.size() >= MAX_CONNECTIONS;
    }
}
