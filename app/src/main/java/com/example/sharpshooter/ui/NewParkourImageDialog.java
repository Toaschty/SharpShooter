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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.sharpshooter.R;

import java.io.ByteArrayOutputStream;

public class NewParkourImageDialog extends DialogFragment
{
    final int contentView;
    private final DialogFragment nextDialog;

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

    @NonNull
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
            mTakePhoto.launch(camera);
        });

        // Add click listener
        continueButton.setOnClickListener(view -> {
            ((PlayerInputDialog) nextDialog).setData(parkourName, playerCount, targetCount, parkourImage);
            nextDialog.show(getParentFragmentManager(), "Next");
            dialog.dismiss();
        });

        return dialog;
    }

    // Callback for selected image from gallery
    final ActivityResultLauncher<Intent> mTakePhoto = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            // If Image Picker was "successful"
            if (result.getResultCode() == RESULT_OK) {
                // Get Bitmap
                assert result.getData() != null;
                Bitmap parkourBitmap = (Bitmap) result.getData().getExtras().get("data");
                parkourImageView.setImageBitmap(parkourBitmap);

                // Save Bitmap as Uri
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                parkourBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(requireContext().getContentResolver(), parkourBitmap, "ParkourImage", null);
                parkourImage = Uri.parse(path);
            }
        }
    });

    public void setData(String parkourName, int playerCount, int targetCount)
    {
        this.parkourName = parkourName;
        this.playerCount = playerCount;
        this.targetCount = targetCount;
    }
}
