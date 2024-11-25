package com.jakub_lewandowski.gwent_backend.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    private static final int MAX_CONNECTIONS = 10;
    private static final AtomicInteger activeConnections = new AtomicInteger(0);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        int currentConnections = activeConnections.get();
        System.out.println("WebSocket connection attempt from " + request.getRemoteAddress());

        if (currentConnections >= MAX_CONNECTIONS) {
            System.out.println("Connection rejected: Too many active players on the server (" + currentConnections + ").");
            response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            return false;
        }

        System.out.println("Connection established: " + (currentConnections + 1) + "/" + MAX_CONNECTIONS);
        activeConnections.incrementAndGet();
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
        System.out.println("WebSocket connection established");
    }


    // To be implemented upon disconnection
    public static void onConnectionClosed() {
        int remainingConnections = activeConnections.decrementAndGet();
        System.out.println("WebSocket connection closed. Remaining connections: " + remainingConnections);
    }

    public static boolean getActiveConnections() {
        return (activeConnections.get() >= MAX_CONNECTIONS);
    }
}
