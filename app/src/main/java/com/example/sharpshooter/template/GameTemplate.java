package com.example.sharpshooter.template;

import com.example.sharpshooter.FirebaseUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameTemplate {

    private boolean active;
    private String gameName;
    private int targetCount;
    private Map<String, Object> player;
    private List<String> playerNames;
    private String date;

    public GameTemplate(boolean active, String gameName, Map<String, Object> player, int targetCount, List<String> playerNames){
        this.active = active;
        this.gameName = gameName;
        this.player = player;
        this.targetCount = targetCount;
        this.playerNames = playerNames;
        this.date = getDateNow();
    }

    public GameTemplate(){}


    private String getDateNow()
    {
        LocalDate myDateObj = LocalDate.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }

    public String getDate() {
        return date;
    }

    public String getGameName() {
        return gameName;
    }

    public boolean isActive() {
        return active;
    }

    public Map<String, Object> getPlayer() {
        return player;
    }
    public String getPlayerTotalScore(String playerName)
    {
        Map<String, Object> player = (Map<String, Object>) FirebaseUtil.GetInstance().gameInstance.getPlayer().get(playerName);
        return player.get("totalScore").toString();
    }
    public ArrayList<Long> getPlayerTargetScore(String playerName)
    {
        Map<String, Object> player = (Map<String, Object>) FirebaseUtil.GetInstance().gameInstance.getPlayer().get(playerName);
        return (ArrayList<Long>) player.get("targetScore");
    }


    public int getPlayerTargetScoreWithId(String playerName, int targetId)
    {
        Map<String, Object> player = (Map<String, Object>) FirebaseUtil.GetInstance().gameInstance.getPlayer().get(playerName);
        ArrayList<Long> score = (ArrayList<Long>) player.get("targetScore");
        return Math.toIntExact(score.get(targetId));
    }

    public int getPlayerBrokenArrowsWithId(String playerName, int targetId)
    {
        Map<String, Object> player = (Map<String, Object>) FirebaseUtil.GetInstance().gameInstance.getPlayer().get(playerName);
        ArrayList<Long> score = (ArrayList<Long>) player.get("brokenArrows");
        return Math.toIntExact(score.get(targetId));
    }


    public int getTargetCount()
    {
        return Math.toIntExact(targetCount);
    }

    public void setPlayerBrokenArrows(String playerName, int targetId, int brokenArrows) {
        Map<String, Object> player = (Map<String, Object>) FirebaseUtil.GetInstance().gameInstance.getPlayer().get(playerName);
        ArrayList<Long> targetScore = (ArrayList<Long>) player.get("brokenArrows");
        targetScore.set(targetId, (long)brokenArrows);
    }

    public void setPlayerTargetScore(String playerName, int targetId, int score)
    {
        Map<String, Object> player = (Map<String, Object>) FirebaseUtil.GetInstance().gameInstance.getPlayer().get(playerName);
        ArrayList<Long> targetScore = (ArrayList<Long>) player.get("targetScore");
        targetScore.set(targetId, (long)score);
    }

    public void setPlayerTotalScore(String playerName, int score)
    {
        Map<String, Object> player = (Map<String, Object>) FirebaseUtil.GetInstance().gameInstance.getPlayer().get(playerName);
        player.put("totalScore", score);
    }


    public List<String> getPlayerNames() {
        return playerNames;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
