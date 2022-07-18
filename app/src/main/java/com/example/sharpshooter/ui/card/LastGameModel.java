package com.example.sharpshooter.ui.card;

import com.example.sharpshooter.R;

public class LastGameModel {

    private String lastGame_name;
    private final String lastGame_date;
    private final int lastGame_playerCount;
    private final int lastGame_targetCount;
    private final int lastGame_image;
    private String gameId;

    public LastGameModel(String name, String date, int playerCount, int targetCount, String gameId) {
        this.lastGame_name = name;
        this.lastGame_date = date;
        this.lastGame_playerCount = playerCount;
        this.lastGame_targetCount = targetCount;
        this.gameId = gameId;
        this.lastGame_image = R.drawable.ic_no_image;
    }

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

    public String getGameId() {
        return gameId;
    }

    public int getLastGame_image() { return lastGame_image; }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}