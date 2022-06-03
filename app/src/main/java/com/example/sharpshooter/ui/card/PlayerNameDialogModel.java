package com.example.sharpshooter.ui.card;

public class PlayerNameDialogModel {
    private String name;
    private boolean account;

    public PlayerNameDialogModel(boolean account)
    {
        this.account = account;
    }

    public boolean isAccount() {
        return account;
    }

    public void setAccount(boolean account) {
        this.account = account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
