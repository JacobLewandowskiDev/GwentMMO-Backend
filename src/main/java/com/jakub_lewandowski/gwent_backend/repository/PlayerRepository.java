package com.jakub_lewandowski.gwent_backend.repository;

import com.jakub_lewandowski.gwent_backend.model.Player;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository {

    // Single player related methods:
    Optional<Player> findPlayerById(long playerId);
    Optional<Player> createPlayer(Player player);
    void deletePlayer(long playerId);
    Optional<Player> updatePlayer(long playerId,Player player);


    // Methods regarding multiple players:
    List<Player> getAllPlayers();
    void deleteAllPlayer();
}
