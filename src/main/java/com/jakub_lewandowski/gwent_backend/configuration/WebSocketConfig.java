package com.jakub_lewandowski.gwent_backend.configuration;

import com.jakub_lewandowski.gwent_backend.model.PlayerWebSocketHandler;
import com.jakub_lewandowski.gwent_backend.model.WebSocketHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/game-socket")
                .setAllowedOrigins("http://localhost:5173")  // adjust for your frontend
                .addInterceptors(new WebSocketHandshakeInterceptor())
                .withSockJS();
        System.out.println("The Server is ready to accept websocket connections from clients.");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");  // for player updates
        config.setApplicationDestinationPrefixes("/app");
    }
}
