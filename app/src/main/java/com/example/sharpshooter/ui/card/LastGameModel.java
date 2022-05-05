package com.example.sharpshooter.ui.card;

public class LastGameModel {

    private String lastGame_name;
    private String lastGame_date;
    private int lastGame_playerCount;
    private int lastGame_targetCount;
    private int lastGame_image;

    // Constructor
    public LastGameModel(String name, String date, int playerCount, int targetCount, int lastGame_image) {
        this.lastGame_name = name;
        this.lastGame_date = date;
        this.lastGame_playerCount = playerCount;
        this.lastGame_targetCount = targetCount;
        this.lastGame_image = lastGame_image;
    }

    // Getter and Setter
    public String getLastGame_name() {
        return lastGame_name;
    }
    public String getLastGame_date() {
        return lastGame_date;
    }
    public int getLastGame_playerCount() { return lastGame_playerCount; }
    public int getLastGame_targetCount() {
        return lastGame_targetCount;
    }

    public void setLastGame_name(String lastGame_name) {
        this.lastGame_name = lastGame_name;
    }

    public int getLastGame_image() {
        return lastGame_image;
    }

    public void setLastGame_image(int lastGame_image) {
        this.lastGame_image = lastGame_image;
    }
}