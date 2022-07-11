package com.example.sharpshooter.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.MainActivity;
import com.example.sharpshooter.databinding.FragmentCurrentGameEndBinding;

public class CurrentGameEnd extends Fragment {
    private FragmentCurrentGameEndBinding binding;

    public CurrentGameEnd() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrentGameEndBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        final Button btnDone = binding.btnDone;
        btnDone.setOnClickListener(btn -> {
            if (FirebaseUtil.getInstance().gameInstance != null) {
                FirebaseUtil.getInstance().gameInstance.setActive(false);
                FirebaseUtil.getInstance().updateGameData("active", FirebaseUtil.getInstance().gameInstance.isActive(), FirebaseUtil.getInstance().activeGame);
            }
            MainActivity.setBottomNavVisibility(false, (MainActivity) root.getContext());
            MainActivity.replaceFragmentToHome( (MainActivity) root.getContext());
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}