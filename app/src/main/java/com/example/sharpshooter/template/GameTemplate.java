package com.example.sharpshooter.template;

public class GameTemplate {

    private boolean active;
    private String gameName;
    private String[] playerNames;

    public GameTemplate(boolean active, String gameName, String[] playerNames){
        this.active = active;
        this.gameName = gameName;
        this.playerNames = playerNames;
    }

    public String getGameName() {
        return gameName;
    }

    public boolean isActive() {
        return active;
    }

    public String[] getPlayerNames() {
        return playerNames;
    }
}
