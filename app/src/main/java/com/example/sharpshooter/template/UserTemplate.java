package com.example.sharpshooter.template;

public class UserTemplate {
    private String name;
    private int broken;
    private int totalGames;
    private int hits;
    private int kills;
    private double killRate;
    private int misses;
    private long points;
    private int shots;

    // Constructor
    public UserTemplate(String name, int broken, int totalGames, int hits, int kills, double killRate, int misses, long points, int shots) {
        this.name = name;
        this.broken = broken;
        this.totalGames = totalGames;
        this.hits = hits;
        this.kills = kills;
        this.killRate = killRate;
        this.misses = misses;
        this.points = points;
        this.shots = shots;
    }

    public UserTemplate() { }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

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

    public double getKillRate() {
        return killRate;
    }

    public int getMisses() {
        return misses;
    }

    public long getPoints() {
        return points;
    }

    public int getShots() {
        return shots;
    }
}
