package com.example.sharpshooter.ui.dashboard;

public class CurrentGameModel {

    private String currentGame_name;

    public CurrentGameModel(String currentGame_name)
    {
        this.currentGame_name = currentGame_name;
    }

    public String getCurrentGame_name() {
        return currentGame_name;
    }

    public void setCurrentGame_name(String currentGame_name) {
        this.currentGame_name = currentGame_name;
    }
}
