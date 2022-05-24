package com.example.sharpshooter.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.R;

public class PlayerInputDialog extends DialogFragment
{
    int contentView;

    private RecyclerView playerView;
    private Button startButton;

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

        playerView = dialog.findViewById(R.id.recyclerViewPlayerNames);
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
