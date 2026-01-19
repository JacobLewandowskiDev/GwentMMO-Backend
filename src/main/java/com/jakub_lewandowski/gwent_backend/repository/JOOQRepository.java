package com.jakub_lewandowski.gwent_backend.repository;

import static com.jakub_lewandowski.gwent_backend.jooq.tables.Players.PLAYERS;

import com.jakub_lewandowski.gwent_backend.jooq.tables.records.PlayersRecord;
import com.jakub_lewandowski.gwent_backend.model.Player;
import com.jakub_lewandowski.gwent_backend.model.ValidationException;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JOOQRepository implements PlayerRepository {

    private final DSLContext context;

    public JOOQRepository(DSLContext context) {
        this.context = context;
    }

    @Override
    public List<Player> getAllPlayers() {
        List<Record> result = context.select()
                .from(PLAYERS)
                .fetch();

        return result.stream()
                .map(record -> new Player(
                        record.get((PLAYERS.ID)),
                        record.get(PLAYERS.USERNAME),
                        record.get((PLAYERS.SPRITE)),
                        record.get(PLAYERS.X_POS),
                        record.get(PLAYERS.Y_POS),
                        record.get((PLAYERS.WINS)),
                        record.get(PLAYERS.LOSSES)
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Player> findPlayerById(long playerId) {
        PlayersRecord record = context.selectFrom(PLAYERS)
                .where(PLAYERS.ID.eq(playerId))
                .fetchOne();

        if (record == null) {
            return Optional.empty();
        }

        return Optional.of(mapRecordToPlayer(record));
    }

    @Override
    public Optional<Player> createPlayer(Player player) {
        var playerExists = context.select()
                .from(PLAYERS)
                .where(PLAYERS.USERNAME.equalIgnoreCase(player.getUsername()))
                .fetchOne();
        if (playerExists != null) {
            throw new ValidationException("Status code:" + HttpStatus.CONFLICT.value() + ", Player under the username: '" + player.getUsername() + "' Already exists.");
        }
        Player newPlayer = new Player(player.getUsername(), player.getSprite());

        PlayersRecord playerRecord = context.insertInto(PLAYERS)
                .set(PLAYERS.USERNAME, newPlayer.getUsername())
                .set(PLAYERS.SPRITE, newPlayer.getSprite())
                .set(PLAYERS.WINS, newPlayer.getWins())
                .set(PLAYERS.LOSSES, newPlayer.getLosses())
                .set(PLAYERS.X_POS, newPlayer.getPositionX())
                .set(PLAYERS.Y_POS, newPlayer.getPositionY())
                .returning()
                .fetchOne();

        if (playerRecord != null) {
            Player createdPlayer = mapRecordToPlayer(playerRecord);
            return Optional.of(createdPlayer);
        } else {
            throw new IllegalStateException("Failed to create player");
        }
    }


    @Override
    public void deletePlayer(long playerId) {
        context.deleteFrom(PLAYERS)
                .where(PLAYERS.ID.eq(playerId))
                .execute();
        System.out.println("Player [" + playerId + "] was deleted from the DB.");
    }

    @Override
    public Optional<Player> updatePlayerPosition(long playerId, Player player) {
        PlayersRecord updatedRecord = context.update(PLAYERS)
                .set(PLAYERS.X_POS, player.getPositionX())
                .set(PLAYERS.Y_POS, player.getPositionY())
                .where(PLAYERS.ID.eq(playerId))
                .returning()
                .fetchOne();

        if (updatedRecord != null) {
            return Optional.of(mapRecordToPlayer(updatedRecord));
        }

        return Optional.empty();
    }


    @Override
    public void deleteAllPlayer() {
        context.deleteFrom(PLAYERS).execute();
        System.out.println("Player table cleared.");
    }


    private Player mapRecordToPlayer(PlayersRecord playersRecord) {
        return new Player(
                playersRecord.getId(),
                playersRecord.getUsername(),
                playersRecord.getSprite(),
                playersRecord.getXPos(),
                playersRecord.getYPos(),
                playersRecord.getWins(),
                playersRecord.getLosses()
        );
    }
}
