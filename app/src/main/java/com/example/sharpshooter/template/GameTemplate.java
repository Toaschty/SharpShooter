package com.example.sharpshooter.template;

public class GameTemplate {

    private boolean active;
    private String gameName;




    public GameTemplate(boolean active, String gameName){
        this.active = active;
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public boolean isActive() {
        return active;
    }
}
