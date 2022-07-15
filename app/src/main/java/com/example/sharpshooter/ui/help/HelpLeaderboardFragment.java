package com.example.sharpshooter.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sharpshooter.databinding.FragmentHelpLeaderboardBinding;

public class HelpLeaderboardFragment extends Fragment {

    private FragmentHelpLeaderboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentHelpLeaderboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Setup close button
        Button btnClose = binding.btnDone;
        btnClose.setOnClickListener(click -> {
            requireActivity().onBackPressed();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
