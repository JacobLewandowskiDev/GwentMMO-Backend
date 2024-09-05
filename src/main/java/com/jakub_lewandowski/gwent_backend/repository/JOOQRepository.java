package com.jakub_lewandowski.gwent_backend.repository;

import static com.jakub_lewandowski.gwent_backend.jooq.tables.Players.PLAYERS;

import com.jakub_lewandowski.gwent_backend.jooq.tables.records.PlayersRecord;
import com.jakub_lewandowski.gwent_backend.model.Player;
import com.jakub_lewandowski.gwent_backend.model.ValidationException;
import org.jooq.DSLContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        var existingPlayer = context.select()
                .from(PLAYERS)
                .where(PLAYERS.ID.eq(playerId))
                .fetchOne();

        assert existingPlayer != null;
        Player player = mapRecordToPlayer((PlayersRecord) existingPlayer);
        return Optional.of(player);
    }

    @Override
    public long createPlayer(Player player) {
        var playerExists = context.select()
                .from(PLAYERS)
                .where(PLAYERS.USERNAME.equalIgnoreCase(player.getUsername()))
                .fetchOne();
        if (playerExists != null) {
            throw new ValidationException("Status code:" + HttpStatus.CONFLICT.value() + ", Player under the username: '" + player.getUsername() + "' Already exists.");
        }
        PlayersRecord createdPlayerRecord = context.insertInto(PLAYERS)
                .set(PLAYERS.USERNAME, player.getUsername())
                .set(PLAYERS.SPRITE, player.getSprite())
                .set(PLAYERS.WINS, player.getWins())
                .set(PLAYERS.LOSSES, player.getLosses())
                .set(PLAYERS.X_POS, player.getPositionX())
                .set(PLAYERS.Y_POS, player.getPositionY())
                .returning()
                .fetchOne();

        if (createdPlayerRecord != null) {
            return createdPlayerRecord.getId();
        } else {
            throw new IllegalStateException("Failed to create player");
        }
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

    private Player mapRecordToPlayer(PlayersRecord playersRecord) {
        Player player = new Player(playersRecord.getId(), playersRecord.getUsername(), playersRecord.getSprite());
        player.setWins(playersRecord.getWins());
        player.setLosses(playersRecord.getLosses());
        player.setPositionX(playersRecord.getXPos());
        player.setPositionY(playersRecord.getYPos());
        return player;
    }
}
