package com.jakub_lewandowski.gwent_backend.model;

public class MovementUpdate {

    private long playerId;
    private int playerPositionX;
    private int playerPositionY;

    public MovementUpdate() {
    }

    public MovementUpdate(long playerId, int playerPositionX, int playerPositionY) {
        this.playerId = playerId;
        this.playerPositionX = playerPositionX;
        this.playerPositionY = playerPositionY;
    }

    public long getPlayerId() {
        return playerId;
    }

    public int getPlayerPositionX() {
        return playerPositionX;
    }

    public int getPlayerPositionY() {
        return playerPositionY;
    }
}
