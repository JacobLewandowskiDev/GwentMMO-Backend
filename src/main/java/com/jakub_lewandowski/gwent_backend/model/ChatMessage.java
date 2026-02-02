package com.jakub_lewandowski.gwent_backend.model;

public class ChatMessage {

    private String playerUsername;
    private String playerMessage;

    public ChatMessage() {};

    public ChatMessage(String playerUsername, String playerMessage) {
        this.playerUsername = playerUsername;
        this.playerMessage = playerMessage;
    };

    public String getPlayerUsername() {
        return playerUsername;
    }

    public String getPlayerMessage() {
        return playerMessage;
    }

    public void setPlayerUsername(String playerUsername) {
        this.playerUsername = playerUsername;
    }

    public void setPlayerMessage(String playerMessage) {
        this.playerMessage = playerMessage;
    }
}
