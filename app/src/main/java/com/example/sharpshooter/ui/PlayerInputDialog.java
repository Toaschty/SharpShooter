package com.example.sharpshooter.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.MainActivity;
import com.example.sharpshooter.R;
import com.example.sharpshooter.template.GameTemplate;
import com.example.sharpshooter.ui.card.PlayerNameDialogAdapter;
import com.example.sharpshooter.ui.card.PlayerNameDialogModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerInputDialog extends DialogFragment
{
    int contentView;

    private Button startButton;
    private RecyclerView playerNameDialogRV;
    private PlayerNameDialogAdapter playerNameDialogAdapter;
    private ArrayList<PlayerNameDialogModel> playerNameDialogModelArrayList;


    private String parkourName;
    private int playerCount;
    private int targetCount;
    private View root;

    public PlayerInputDialog(int contentView, View view)
    {
        this.root = view;
        this.contentView = contentView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Create actual dialog
        Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(contentView);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);

        playerNameDialogRV = dialog.findViewById(R.id.recyclerViewPlayerNames);
        playerNameDialogModelArrayList = new ArrayList<>();
        for (int i = 0; i < playerCount; i++) {
            if (i == 0)
            {
                playerNameDialogModelArrayList.add(new PlayerNameDialogModel(true));
            }else
            {
                playerNameDialogModelArrayList.add(new PlayerNameDialogModel(false));
            }
        }

        playerNameDialogAdapter = new PlayerNameDialogAdapter(dialog.getContext(), playerNameDialogModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(dialog.getContext(), LinearLayoutManager.VERTICAL, false);

        playerNameDialogRV.setLayoutManager(linearLayoutManager);
        playerNameDialogRV.setAdapter(playerNameDialogAdapter);

        List<String> playerNames = new ArrayList<>();

        // Add click listener
        startButton = dialog.findViewById(R.id.newParkour_start);
        startButton.setOnClickListener(view -> {
            for (int i = 0; i < playerCount; i++) {
                playerNames.add(playerNameDialogAdapter.getName(i));
            }
            if (FirebaseUtil.getInstance().gameInstance != null) {
                FirebaseUtil.getInstance().gameInstance.setActive(false);
                FirebaseUtil.getInstance().updateGameData("active", FirebaseUtil.getInstance().gameInstance.isActive(), FirebaseUtil.getInstance().activeGame);
            }
            FirebaseUtil.getInstance().createNewGameData(new GameTemplate(true, parkourName,setPlayer(playerNames) , targetCount, playerNames));
            FirebaseUtil.getInstance().initGameInstance(() ->{
                MainActivity.setBottomNavVisibility(true, (MainActivity) root.getContext());
                MainActivity.replaceFragment( (MainActivity) root.getContext());
            });
            dialog.dismiss();
        });

        return dialog;
    }

    public Map<String, Object> setPlayer(List<String> playerNames)
    {
        Map<String, Object> player = new HashMap<>();
        ArrayList<Long> targetScore = new ArrayList<>(Collections.nCopies(targetCount, 0L));
        ArrayList<Long> brokenArrows = new ArrayList<>(Collections.nCopies(targetCount, 0L));

        playerNames.forEach(playerName -> {
            Map<String, Object> playerObject = new HashMap<>();
            playerObject.put("targetScore", targetScore);
            playerObject.put("totalScore", 0);
            playerObject.put("brokenArrows", brokenArrows);
            player.put(playerName.toString(), playerObject);
        });

        return player;
    }

    public void setData(String parkourName, int playerCount, int targetCount)
    {
        this.parkourName = parkourName;
        this.playerCount = playerCount;
        this.targetCount = targetCount;
    }




}
