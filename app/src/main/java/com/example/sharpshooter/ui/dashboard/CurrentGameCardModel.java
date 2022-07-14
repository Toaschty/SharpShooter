package com.example.sharpshooter.ui.dashboard;

public class CurrentGameCardModel {
    private String player_name;
    private int score;
    private final int targetId;

    public CurrentGameCardModel(String player_name, int score, int targetId)
    {
        this.player_name = player_name;
        this.score = score;
        this.targetId = targetId;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public int getScore() {
        return score;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTargetId() {
        return targetId;
    }
}
