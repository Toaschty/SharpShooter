package com.example.sharpshooter.ui;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.example.sharpshooter.R;

import java.io.IOException;

public class NewParkourImageDialog extends DialogFragment
{
    int contentView;
    private DialogFragment nextDialog;

    private Button continueButton;
    private ImageView parkourImageView;
    private Uri parkourImage;

    private String parkourName;
    private int playerCount;
    private int targetCount;

    public NewParkourImageDialog(int contentView, DialogFragment nextDialog)
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
        parkourImageView = dialog.findViewById(R.id.parkourImage);
        continueButton = dialog.findViewById(R.id.newParkour_continue);

        // Image Picker Button
        parkourImageView.setOnClickListener(view -> {
            // Start Image Picker Intent
            Intent gallery = new Intent();
            gallery.setType("image/*");
            gallery.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(gallery, "Select Picture"), 6969);
        });

        // Add click listener
        continueButton.setOnClickListener(view -> {
            ((PlayerInputDialog) nextDialog).setData(parkourName, playerCount, targetCount, parkourImage);
            nextDialog.show(getParentFragmentManager(), "Next");
            dialog.dismiss();
        });

        return dialog;
    }

    // Handle Image Picker Result
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If Image Picker was "successful"
        if (resultCode == RESULT_OK && requestCode == 6969) {
            // Set selected image
            Uri img = data.getData();
            parkourImage = img;

            // Convert uri to bitmap -> Set picture for immediate feedback
            try {
                Bitmap uriBitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), img);
                parkourImageView.setImageBitmap(uriBitmap);
            } catch (IOException ignored) {}
        }
    }

    public void setData(String parkourName, int playerCount, int targetCount)
    {
        this.parkourName = parkourName;
        this.playerCount = playerCount;
        this.targetCount = targetCount;
    }
}
