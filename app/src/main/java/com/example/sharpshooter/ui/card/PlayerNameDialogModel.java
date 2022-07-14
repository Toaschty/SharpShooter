package com.example.sharpshooter.ui.card;

public class PlayerNameDialogModel {
    private String name;
    private final int count;
    private boolean account;

    public PlayerNameDialogModel(boolean account, int count)
    {
        this.account = account;
        this.count = count;
    }

    public boolean isAccount() {
        return account;
    }

    public void setAccount(boolean account) {
        this.account = account;
    }

    public int getCount() {
        return count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
