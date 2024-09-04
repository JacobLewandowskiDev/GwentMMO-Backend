package com.jakub_lewandowski.gwent_backend.repository;

import static com.jakub_lewandowski.gwent_backend.jooq.tables.Players.PLAYERS;

import com.jakub_lewandowski.gwent_backend.jooq.tables.records.PlayersRecord;
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
        PlayersRecord createdPlayerRecord = context.insertInto(PLAYERS)
                .set(PLAYERS.USERNAME, player.getUsername())
                .set(PLAYERS.SPRITE, player.getSprite())
                .set(PLAYERS.WINS, player.getWins())
                .set(PLAYERS.LOSSES, player.getLosses())
                .set(PLAYERS.LOSSES, player.getLosses())
                .set(PLAYERS.X_POS, player.getPositionX())
                .set(PLAYERS.Y_POS, player.getPositionY())
                .returning()
                .fetchOne();
        return createdPlayerRecord.getId();
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
