package com.jakub_lewandowski.gwent_backend.service;

import com.jakub_lewandowski.gwent_backend.model.MovementUpdate;
import com.jakub_lewandowski.gwent_backend.model.Player;
import com.jakub_lewandowski.gwent_backend.model.ValidationException;
import com.jakub_lewandowski.gwent_backend.model.WebSocketEventListener;
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

    public ResponseEntity<?> createPlayer(Player player) {
        System.out.println("createPlayer() method called.");
        if(WebSocketEventListener.currentPlayerCount()) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Connection rejected: The Server has reached the Maximum active player count.");
        }
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(playerRepository.createPlayer(player));
        } catch (ValidationException e) {
            System.out.println("Error occurred while creating a new Player: [" + e.getMessage() + "]");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    public Optional<Player> getPlayerById(long playerId) {
        System.out.println("getPlayerById() method called for: [" + playerId + "]");
        return playerRepository.findPlayerById(playerId);
    }

    public List<Player> getAllPlayers() {
        System.out.println("getAllPLayers() method called");
        return playerRepository.getAllPlayers();
    }

    public void deletePlayer(long playerId) {
        System.out.println("deletePlayer() method called for: [" + playerId + "]");
        playerRepository.deletePlayer(playerId);
    }

    public void updatePlayerPosition(MovementUpdate movementUpdate) {
        System.out.println("Update for id[" + movementUpdate.getPlayerId() + "] recieved, new position is x: " + movementUpdate.getPlayerPositionX() + ", y: " + movementUpdate.getPlayerPositionY());
    }
}
