package com.example.sharpshooter.ui.account;

import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.finishAffinity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.WelcomeActivity;
import com.example.sharpshooter.databinding.FragmentAccountBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    private TextView playerName;
    private ImageView playerImage;
    private Button btn_logout;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        playerName = binding.accountName;
        playerImage = binding.accountPicture;
        btn_logout = binding.btnLogout;

        // Load user name
        FirebaseUtil.getInstance().database.collection("users")
                .document(Objects.requireNonNull(FirebaseUtil.getInstance().authentication.getUid())).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                playerName.setText(documentSnapshot.get("name").toString());
            }
        });

        // Load user image
        StorageReference photoReference = FirebaseUtil.getInstance().storage.child("users/" + FirebaseUtil.getInstance().authentication.getUid());
        photoReference.getBytes(8000000).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Save loaded bytes in Bitmap
                Bitmap bmp_image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                playerImage.setImageBitmap(bmp_image);
            }
        });

        // Image Picker
        playerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start Image Picker Intent
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 6969);
            }
        });

        // Logout Button
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out current user
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();

                // "Restart" application by loading welcome page
                Intent welcome_intent = new Intent(getActivity(), WelcomeActivity.class);
                startActivity(welcome_intent);
                finishAffinity(getActivity());
            }
        });

        return root;
    }

    // Handle Image Picker Result
    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If Image Picker was "successful"
        if (resultCode == RESULT_OK && requestCode == 6969) {

            // Set selected image
            Uri img = data.getData();
            playerImage.setImageURI(img);

            // Upload image to firebase
            FirebaseUtil.getInstance().uploadAccountImage(img);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}