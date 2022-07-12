package com.example.sharpshooter.ui.account;

import static android.app.Activity.RESULT_OK;
import static androidx.core.app.ActivityCompat.finishAffinity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.R;
import com.example.sharpshooter.WelcomeActivity;
import com.example.sharpshooter.databinding.FragmentAccountBinding;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    private TextView playerName;
    private ImageView playerImage;
    private Button btn_logout;
    private Button btn_statistics;
    private Button btn_playedGames;
    private Button btn_savedGames;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get references
        playerName = binding.accountName;
        playerImage = binding.accountPicture;
        btn_logout = binding.btnLogout;
        btn_statistics = binding.btnStatistics;
        btn_playedGames = binding.btnLastGames;
        btn_savedGames = binding.btnSavedParkours;
        // Load user name
        playerName.setText(FirebaseUtil.GetInstance().userInstance.getName());

        // Load account image
        playerImage.setImageBitmap(FirebaseUtil.GetInstance().userProfilePicture);

        // Image Picker Button
        playerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start Image Picker Intent
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), 6969);
            }
        });

        // Statistics Button
        btn_statistics.setOnClickListener(view -> {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.action_navigation_account_to_accountFragmentStatistics);
        });

        btn_playedGames.setOnClickListener(view -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.action_navigation_account_to_playedGames);
        });

        btn_savedGames.setOnClickListener(view -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.action_navigation_account_to_savedGameFragment);
        });

        // Logout Button
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sign out current user
                FirebaseUtil.GetInstance().authentication.signOut();

                // Destroy instance
                FirebaseUtil.GetInstance().destroyInstance();

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
            FirebaseUtil.GetInstance().uploadAccountImage(img);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}