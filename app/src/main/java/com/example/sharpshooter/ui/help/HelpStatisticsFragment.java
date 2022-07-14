package com.example.sharpshooter.ui.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sharpshooter.databinding.FragmentHelpLeaderboardBinding;
import com.example.sharpshooter.databinding.FragmentHelpStatisticsBinding;

public class HelpStatisticsFragment extends Fragment {

    private FragmentHelpStatisticsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentHelpStatisticsBinding.inflate(inflater, container, false);
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
