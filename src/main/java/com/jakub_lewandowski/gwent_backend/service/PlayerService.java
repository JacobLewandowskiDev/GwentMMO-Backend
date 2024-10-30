package com.jakub_lewandowski.gwent_backend.service;

import com.jakub_lewandowski.gwent_backend.model.Player;
import com.jakub_lewandowski.gwent_backend.model.ValidationException;
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
        System.out.println("createPlayer() method called, creating new Player.");
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(playerRepository.createPlayer(player));
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // Open a websocket connection for player and start the game.
    public Optional<Player> startGame(long playerId) {
        System.out.println("startGame() method called, attempting to start game...");
        return playerRepository.findPlayerById(playerId);
    }

    public List<Player> getAllPlayers() {
        System.out.println("getAllPLayers() called");
        return playerRepository.getAllPlayers();
    }
}
