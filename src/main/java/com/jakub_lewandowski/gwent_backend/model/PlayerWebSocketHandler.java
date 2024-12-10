package com.jakub_lewandowski.gwent_backend.model;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerWebSocketHandler extends AbstractWebSocketHandler {

    // A map to store active player sessions by their playerId
    private static final Map<Long, WebSocketSession> playerSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Long playerId = (Long) session.getAttributes().get("playerId");
        if (playerId != null) {
            playerSessions.put(playerId, session);  // Store session by playerId
            System.out.println("Player " + playerId + " connected. Active players: " + playerSessions.size());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Long playerId = (Long) session.getAttributes().get("playerId");
        if (playerId != null) {
            playerSessions.remove(playerId);  // Remove player session from the map
            System.out.println("Player " + playerId + " disconnected. Active players: " + playerSessions.size());
        }
    }

    // Method to close a session based on playerId
    public static void closeConnection(Long playerId) {
        WebSocketSession session = playerSessions.get(playerId);
        if (session != null && session.isOpen()) {
            try {
                session.close();
                playerSessions.remove(playerId);
                System.out.println("WebSocket connection closed for player: " + playerId);
            } catch (Exception e) {
                System.out.println("Error closing WebSocket connection for player: " + playerId);
            }
        } else {
            System.out.println("No active WebSocket connection for player: " + playerId);
        }
    }

    // Get active sessions for debugging purposes
    public static Map<Long, WebSocketSession> getActiveSessions() {
        return playerSessions;
    }
}
