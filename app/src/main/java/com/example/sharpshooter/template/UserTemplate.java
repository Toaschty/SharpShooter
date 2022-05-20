package com.example.sharpshooter.template;

public class UserTemplate {
    private String name;
    private int broken;
    private int totalGames;
    private int hits;
    private int kills;
    private double kd;
    private int misses;
    private int points;
    private int shots;

    // Constructor
    public UserTemplate(String name, int broken, int totalGames, int hits, int kills, double kd, int misses, int points, int shots) {
        this.name = name;
        this.broken = broken;
        this.totalGames = totalGames;
        this.hits = hits;
        this.kills = kills;
        this.kd = kd;
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

    public void setBroken(int broken) { this.broken = broken; }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) { this.totalGames = totalGames; }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) { this.hits = hits; }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) { this.kills = kills; }

    public double getKd() {
        return kd;
    }

    public void setKd(double kd) { this.kd = kd; }

    public int getMisses() {
        return misses;
    }

    public void setMisses(int misses) { this.misses = misses; }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) { this.points = points; }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) { this.shots = shots; }
}
