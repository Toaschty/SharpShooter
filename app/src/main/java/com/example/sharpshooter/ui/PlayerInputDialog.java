package com.example.sharpshooter.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.R;
import com.example.sharpshooter.ui.card.LastGameModel;
import com.example.sharpshooter.ui.card.PlayerNameDialogAdapter;
import com.example.sharpshooter.ui.card.PlayerNameDialogModel;

import java.util.ArrayList;

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

    public PlayerInputDialog(int contentView)
    {
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


        // Add click listener
        startButton = dialog.findViewById(R.id.newParkour_start);
        startButton.setOnClickListener(view -> {

        });

        return dialog;
    }

    public void setData(String parkourName, int playerCount, int targetCount)
    {
        this.parkourName = parkourName;
        this.playerCount = playerCount;
        this.targetCount = targetCount;
    }
}
