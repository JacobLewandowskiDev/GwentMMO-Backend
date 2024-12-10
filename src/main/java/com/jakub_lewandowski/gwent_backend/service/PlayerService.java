package com.jakub_lewandowski.gwent_backend.service;

import com.jakub_lewandowski.gwent_backend.model.Player;
import com.jakub_lewandowski.gwent_backend.model.ValidationException;
import com.jakub_lewandowski.gwent_backend.model.WebSocketHandshakeInterceptor;
import com.jakub_lewandowski.gwent_backend.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    // Attempt to create player in DB, and validate if username exists.
    public ResponseEntity<?> createPlayer(Player player) {
        if(WebSocketHandshakeInterceptor.getActiveConnections()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Maximum number of players on the server has been reached 10/10. Please try again later.");
        }
        try{
            System.out.println("createPlayer() method called.");
            return ResponseEntity.status(HttpStatus.CREATED).body(playerRepository.createPlayer(player));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Open a websocket connection for player and start the game.
    public Optional<Player> getPlayerbyId(long playerId) {
        System.out.println("getPlayerById() method called");
        return playerRepository.findPlayerById(playerId);
    }

    public List<Player> getAllPlayers() {
        System.out.println("getAllPLayers() called");
        return playerRepository.getAllPlayers();
    }

    public void deletePlayer(long playerId) {
        System.out.println("deletePlayer() called for: " + playerId);
        playerRepository.deletePlayer(playerId);
    }
}
