package com.jakub_lewandowski.gwent_backend.model;

public class MovementUpdate {

    private long playerId;
    private String username;
    private int playerPositionX;
    private int playerPositionY;

    public MovementUpdate() {
    }

    public MovementUpdate(long playerId, String username, int playerPositionX, int playerPositionY) {
        this.playerId = playerId;
        this.username = username;
        this.playerPositionX = playerPositionX;
        this.playerPositionY = playerPositionY;
    }

    public long getPlayerId() {
        return playerId;
    }

    public String getUsername() {
        return username;
    }

    public int getPlayerPositionX() {
        return playerPositionX;
    }

    public int getPlayerPositionY() {
        return playerPositionY;
    }
}
