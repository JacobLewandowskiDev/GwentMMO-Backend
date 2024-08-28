package com.jakub_lewandowski.gwent_backend.model;

public class Player {
    private long id;
    private String username;
    private int profileImg;
    private int positionX;
    private int positionY;
    private int wins;
    private int losses;

    public Player(long id, String username, int profileImg) {
        this.id = id;
        this.username = username;
        this.profileImg = profileImg;
        this.positionX = 600;
        this.positionY = 310;
        this.wins = 0;
        this.losses = 0;
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

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }
}
