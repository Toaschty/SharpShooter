package com.example.sharpshooter.template;

public class UserTemplate {
    private int broken;
    private int totalGames;
    private int hits;
    private int kills;
    private double kd;
    private int misses;
    private int points;
    private int shots;

    // Constructor
    public UserTemplate(int broken, int totalGames, int hits, int kills, double kd, int misses, int points, int shots) {
        this.broken = broken ;
        this.totalGames = totalGames;
        this.hits = hits;
        this.kills = kills;
        this.kd = kd;
        this.misses = misses;
        this.points = points;
        this.shots = shots;
    }

    public int getBroken() {
        return broken;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public int getHits() {
        return hits;
    }

    public int getKills() {
        return kills;
    }

    public double getKd() {
        return kd;
    }

    public int getMisses() {
        return misses;
    }

    public int getPoints() {
        return points;
    }

    public int getShots() {
        return shots;
    }
}
