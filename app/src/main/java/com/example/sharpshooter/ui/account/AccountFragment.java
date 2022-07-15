package com.example.sharpshooter.ui.account;

import static androidx.core.app.ActivityCompat.finishAffinity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.R;
import com.example.sharpshooter.WelcomeActivity;
import com.example.sharpshooter.databinding.FragmentAccountBinding;

import java.io.IOException;

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
        if (FirebaseUtil.GetInstance().userProfilePicture != null)
            playerImage.setImageBitmap(FirebaseUtil.GetInstance().userProfilePicture);

        // Image Picker Button
        playerImage.setOnClickListener(view -> {
            // Start Image Picker Intent
            mGetContent.launch("image/*");
        });

        // Statistics Button
        btn_statistics.setOnClickListener(view -> Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.action_navigation_account_to_accountFragmentStatistics));

        btn_playedGames.setOnClickListener(view -> Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.action_navigation_account_to_playedGames));

        btn_savedGames.setOnClickListener(view -> Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.action_navigation_account_to_savedGameFragment));

        // Logout Button
        btn_logout.setOnClickListener(view -> {
            // Sign out current user
            FirebaseUtil.GetInstance().authentication.signOut();

            // Destroy instance
            FirebaseUtil.GetInstance().destroyInstance();

            // "Restart" application by loading welcome page
            Intent welcome_intent = new Intent(requireActivity(), WelcomeActivity.class);
            startActivity(welcome_intent);
            finishAffinity(requireActivity());
        });

        return root;
    }

    // Callback for selected image from gallery
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<>() {
        @Override
        public void onActivityResult(Uri result) {
            playerImage.setImageURI(result);

            // Upload image to firebase
            FirebaseUtil.GetInstance().uploadAccountImage(result);

            // Convert uri to bitmap -> Set picture for immediate feedback
            try {
                FirebaseUtil.GetInstance().userProfilePicture = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), result);
            } catch (IOException ignored) {
            }
        }
    });

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}