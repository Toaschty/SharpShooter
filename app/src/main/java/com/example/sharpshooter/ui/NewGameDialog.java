package com.example.sharpshooter.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import com.example.sharpshooter.R;

public class NewGameDialog extends DialogFragment
{
    private final int contentView;
    private final DialogFragment nextDialog;
    private Button newParkourButton;
    private Button loadParkourBtn;

    public NewGameDialog(int contentView, DialogFragment nextDialog)
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

        // Add click listener
        newParkourButton = dialog.findViewById(R.id.newGame_newParkour);
        newParkourButton.setOnClickListener(view -> {
            nextDialog.show(getParentFragmentManager(), "Next");
            dialog.dismiss();
        });

        loadParkourBtn = dialog.findViewById(R.id.newParkour_continue);
        loadParkourBtn.setOnClickListener(view -> {
            dialog.dismiss();
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.action_navigation_home_to_loadGame);
        });

        return dialog;
    }
}
