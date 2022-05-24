package com.example.sharpshooter.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;

import com.example.sharpshooter.R;

public class NewGameDialog extends DialogFragment
{
    private int contentView;
    private DialogFragment nextDialog;
    private Button newParkourButton;

    public NewGameDialog(int contentView, DialogFragment nextDialog)
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

        // Add click listener
        newParkourButton = dialog.findViewById(R.id.newGame_newParkour);
        newParkourButton.setOnClickListener(view -> {
            nextDialog.show(getParentFragmentManager(), "Next");
            dialog.dismiss();
        });

        return dialog;
    }
}
