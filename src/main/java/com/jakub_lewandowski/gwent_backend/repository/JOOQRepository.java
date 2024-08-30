package com.jakub_lewandowski.gwent_backend.repository;

import com.jakub_lewandowski.gwent_backend.model.Player;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JOOQRepository implements PlayerRepository {

    private final DSLContext context;

    public JOOQRepository(DSLContext context) {
        this.context = context;
    }

    @Override
    public Optional<Player> findPlayerById(long playerId) {
        return Optional.empty();
    }

    @Override
    public long createPlayer(Player player) {
//        Player createdPlayer = context.insertInto(PLAYERS)
//                .set(PLAYER.USERNAME, player.getUsername())
//                .set(PLAYER.SPRITE, player.getSprite())
//                .set(PLAYER.WINS, player.getWins())
//                .set(PLAYER.LOSSES, player.getLosses())
//                .set(PLAYER.LOSSES, player.getLosses())
//                .set(PLAYER.POS_X, player.getPositionX())
//                .set(PLAYER.POS_Y, player.getPositionY())
//                .returning()
//                .fetchOne();
//        return createdPlayer.getId();
        return 1;
    }

    @Override
    public Optional<Player> deletePlayer(long playerId) {
        return Optional.empty();
    }

    @Override
    public void updatePlayer(long playerId, Player player) {

    }

    @Override
    public List<Player> findAllPlayers() {
        return null;
    }

    @Override
    public void deleteAllPlayer() {

    }
}
