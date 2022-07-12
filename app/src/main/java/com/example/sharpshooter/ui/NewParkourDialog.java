package com.example.sharpshooter.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import com.example.sharpshooter.R;

public class NewParkourDialog extends DialogFragment
{
    int contentView;
    private DialogFragment nextDialog;

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
        switch (playerId)
        {
            case R.id.newParkourPlayerButton1: selectedPlayerCount = 1; break;
            case R.id.newParkourPlayerButton2: selectedPlayerCount = 2; break;
            case R.id.newParkourPlayerButton3: selectedPlayerCount = 3; break;
        }

        if (playerCount.getText().toString().length() > 0)
            selectedPlayerCount = Integer.valueOf(playerCount.getText().toString());

        // Targetcount
        int targetId = targetGroup.getCheckedRadioButtonId();
        switch (targetId)
        {
            case R.id.newParkourTargetButton1: selectedTargetCount = 5; break;
            case R.id.newParkourTargetButton2: selectedTargetCount = 10; break;
            case R.id.newParkourTargetButton3: selectedTargetCount = 15; break;
        }

        if (targetCount.getText().toString().length() > 0)
            selectedTargetCount = Integer.valueOf(targetCount.getText().toString());
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
        if (selectedTargetCount == 0)
            return false;

        return true;
    }

    private void SelectEditText(EditText text)
    {
        text.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.radio_button_right_checked, null));
        text.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
    }

    private void DeselectEditText(EditText text)
    {
        text.setText("");
        text.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.radio_button_right_unchecked, null));
        text.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
    }
}
