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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PlayerService {

    @Autowired
    private final PlayerRepository playerRepository;

    private final Map<Long, Long> lastSavedTimestamps = new ConcurrentHashMap<>();
    private final long SAVE_INTERVAL_MS = 5000; // save at most once every 5 seconds

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

    @Transactional
    public void updatePlayerPosition(MovementUpdate movementUpdate) {
        long now = System.currentTimeMillis();
        Long lastSaved = lastSavedTimestamps.get(movementUpdate.getPlayerId());

        if (lastSaved != null && (now - lastSaved) < SAVE_INTERVAL_MS) {
            return;
        }

        Optional<Player> optionalPlayer = playerRepository.findPlayerById(movementUpdate.getPlayerId());

        if (optionalPlayer.isPresent()) {
            Player player = optionalPlayer.get();

            player.setPositionX(movementUpdate.getPlayerPositionX());
            player.setPositionY(movementUpdate.getPlayerPositionY());

            Optional<Player> updatedPlayer = playerRepository.updatePlayerPosition(player.getId(), player);

            if (updatedPlayer.isPresent()) {
                lastSavedTimestamps.put(movementUpdate.getPlayerId(), now);
                player = updatedPlayer.get(); // get the latest record
                System.out.println("Saved position for player " + player.getUsername() +
                        " with id: " + player.getId() +
                        " x:" + player.getPositionX() +
                        ", y:" + player.getPositionY());
            } else {
                System.out.println("Update failed or player not found: " + movementUpdate.getPlayerId());
            }
        } else {
            System.out.println("Player with ID " + movementUpdate.getPlayerId() + " not found.");
        }
    }
}
