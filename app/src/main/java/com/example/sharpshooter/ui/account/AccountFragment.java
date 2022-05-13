package com.example.sharpshooter.ui.account;

import static androidx.core.app.ActivityCompat.finishAffinity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sharpshooter.MainActivity;
import com.example.sharpshooter.R;
import com.example.sharpshooter.WelcomeActivity;
import com.example.sharpshooter.databinding.FragmentAccountBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get button references
        Button btn_logout = binding.btnLogout;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}