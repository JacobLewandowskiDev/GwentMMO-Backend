package com.jakub_lewandowski.gwent_backend.model;

import org.springframework.context.event.EventListener;
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


    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String playerId = headerAccessor.getFirstNativeHeader("playerId");

        if (playerId != null && activeSessions.size() < MAX_CONNECTIONS) {
            activeSessions.put(sessionId, playerId);
            System.out.println("Player with id: [" + playerId + "] has joined the server. Server status: [" + activeSessions.size() + "/" + MAX_CONNECTIONS + "].");
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        String playerId = activeSessions.remove(sessionId);

        if (playerId != null) {
            System.out.println("Player with id: [" + playerId + "] has disconnected from the server. Server status: [" + activeSessions.size() + "/" + MAX_CONNECTIONS + "].");
        }
    }

    public static boolean currentPlayerCount() {
        return activeSessions.size() >= MAX_CONNECTIONS;
    }
}
