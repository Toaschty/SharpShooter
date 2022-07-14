package com.example.sharpshooter.ui.dashboard;

public class CurrentGameModel {

    private String currentGame_name;
    private final int targetId;

    public CurrentGameModel(String currentGame_name, int targetId)
    {
        this.currentGame_name = currentGame_name;
        this.targetId = targetId;
    }

    public String getCurrentGame_name() {
        return currentGame_name;
    }

    public void setCurrentGame_name(String currentGame_name) {
        this.currentGame_name = currentGame_name;
    }

    public int getTargetId() {
        return targetId;
    }
}
