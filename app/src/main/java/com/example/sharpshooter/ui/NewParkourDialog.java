package com.example.sharpshooter.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.example.sharpshooter.R;

import java.util.Objects;

public class NewParkourDialog extends DialogFragment
{
    final int contentView;
    private final DialogFragment nextDialog;

    private EditText parkourName;
    private EditText playerCount;
    private EditText targetCount;
    private RadioGroup playerGroup;
    private RadioGroup targetGroup;
    private Button continueButton;

    private int selectedPlayerCount;
    private int selectedTargetCount;

    public NewParkourDialog(int contentView, DialogFragment nextDialog)
    {
        this.contentView = contentView;
        this.nextDialog = nextDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Create actual dialog
        Dialog dialog = new Dialog(getActivity());

        dialog.setContentView(contentView);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);

        // Get references
        parkourName = dialog.findViewById(R.id.newParkourNameInput);
        playerCount = dialog.findViewById(R.id.newParkourPlayersCount);
        targetCount = dialog.findViewById(R.id.newParkourTargetCount);
        playerGroup = dialog.findViewById(R.id.newParkourPlayersGroup);
        targetGroup = dialog.findViewById(R.id.newParkourTargetGroup);
        continueButton = dialog.findViewById(R.id.newParkour_continue);

        // Clear custom input Listener
        playerGroup.setOnCheckedChangeListener((group, i) -> {
            if (i > 0)
                DeselectEditText(playerCount);
        });

        targetGroup.setOnCheckedChangeListener((group, i) -> {
            if (i > 0)
                DeselectEditText(targetCount);
        });

        // ---

        playerCount.setOnClickListener(view -> {
            playerGroup.clearCheck();
            SelectEditText(playerCount);
        });

        playerCount.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
            {
                playerGroup.clearCheck();
                SelectEditText(playerCount);
            }
        });

        // ---

        targetCount.setOnClickListener(view -> {
            targetGroup.clearCheck();
            SelectEditText(targetCount);
        });

        targetCount.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
            {
                targetGroup.clearCheck();
                SelectEditText(targetCount);
            }
        });

        // Add click listener
        continueButton.setOnClickListener(view -> {
            CollectData();
            if (checkInput())
            {
                ((NewParkourImageDialog) nextDialog).setData(parkourName.getText().toString(), selectedPlayerCount, selectedTargetCount);
                nextDialog.show(getParentFragmentManager(), "Next");
                dialog.dismiss();
            }
        });

        return dialog;
    }

    private void CollectData()
    {
        // Playercount
        int playerId = playerGroup.getCheckedRadioButtonId();

        // if else instead of Switch case because since Gradle 8.0 Resources are not final anymore. switch statement requires all the case labels to be constant at compile time.
        if (playerId == R.id.newParkourPlayerButton1)
            selectedPlayerCount = 1;
        else if (playerId == R.id.newParkourPlayerButton2)
            selectedPlayerCount = 2;
        else if (playerId == R.id.newParkourPlayerButton3)
            selectedPlayerCount = 3;


        if (playerCount.getText().toString().length() > 0)
            selectedPlayerCount = Integer.parseInt(playerCount.getText().toString());

        // Targetcount
        int targetId = targetGroup.getCheckedRadioButtonId();
        if (targetId == R.id.newParkourTargetButton1)
            selectedTargetCount = 5;
        else if (targetId == R.id.newParkourTargetButton2)
            selectedTargetCount = 10;
        else if (targetId == R.id.newParkourTargetButton3)
            selectedTargetCount = 15;

        if (targetCount.getText().toString().length() > 0)
            selectedTargetCount = Integer.parseInt(targetCount.getText().toString());
    }

    private boolean checkInput()
    {
        // Name
        if (parkourName.getText().toString().equals(""))
            return false;

        // Playercount selected?
        if (selectedPlayerCount == 0)
            return false;

        // Targetcount selected?
        return selectedTargetCount != 0;
    }

    private void SelectEditText(EditText text)
    {
        text.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.radio_button_right_checked, null));
        text.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext(), "getContext must not be null (SelectEditText)"), R.color.white));
    }

    private void DeselectEditText(EditText text)
    {
        text.setText("");
        text.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.radio_button_right_unchecked, null));
        text.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext(), "getContext must not be null (DeselectEditText)"), R.color.black));
    }
}
