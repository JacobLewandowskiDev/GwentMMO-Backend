package com.jakub_lewandowski.gwent_backend.model;

import java.util.HashMap;

public class Player {
    private long id;
    private String username;
    private int profileImg;
    private int positionX;
    private int positionY;
    private HashMap<String, Integer> scoreboard;

    public Player(long id, String username, int profileImg, int positionX, int positionY) {
        this.id = id;
        this.username = username;
        this.profileImg = profileImg;
        this.positionX = positionX;
        this.positionY = positionY;
        this.scoreboard = new HashMap<>(2);
        this.scoreboard.put("wins", 0);
        this.scoreboard.put("losses", 0);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public int getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(int profileImg) {
        this.profileImg = profileImg;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public HashMap<String, Integer> getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(HashMap<String, Integer> scoreboard) {
        this.scoreboard = scoreboard;
    }
}
