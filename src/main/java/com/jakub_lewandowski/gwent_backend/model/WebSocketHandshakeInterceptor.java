package com.jakub_lewandowski.gwent_backend.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("Connection attempt from: " + request.getRemoteAddress());
        if (WebSocketEventListener.currentPlayerCount()) {
            System.out.println("Connection rejected: The Server has reached the Maximum active player count.");
            response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return false;
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println("WebSocket connection has been established successfully.");
    }
}
