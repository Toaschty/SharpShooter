package com.example.sharpshooter.ui.card;

public class LeaderboardModel {

    private String playerName;
    private int points;
    private int maxPoints;

    public LeaderboardModel(String playerName, int points, int maxPoints)
    {
        this.playerName = playerName;
        this.points = points;
        this.maxPoints = maxPoints;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPoints() {
        return points;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }
}
