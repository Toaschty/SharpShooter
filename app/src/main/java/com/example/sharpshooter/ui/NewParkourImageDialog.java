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

import java.io.ByteArrayOutputStream;
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
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(Intent.createChooser(camera, "Take Picture"), 6969);
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
            // Get Bitmap
            Bitmap parkourBitmap = (Bitmap) data.getExtras().get("data");
            parkourImageView.setImageBitmap(parkourBitmap);

            // Save Bitmap as Uri
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            parkourBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), parkourBitmap, "ParkourImage", null);
            parkourImage = Uri.parse(path);
        }
    }

    public void setData(String parkourName, int playerCount, int targetCount)
    {
        this.parkourName = parkourName;
        this.playerCount = playerCount;
        this.targetCount = targetCount;
    }
}
