package com.example.sharpshooter.ui.card;

public class CurrentGameWinModel {
    private String playerName;

    public CurrentGameWinModel(String playerName)
    {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
