package com.example.sharpshooter.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentCurrentGameBinding;
import com.example.sharpshooter.databinding.FragmentDashboardBinding;


public class CurrentGame extends Fragment {

    private String title;
    private FragmentCurrentGameBinding binding;

    public CurrentGame() {
    }

    public CurrentGame(String title) {
        this.title = title;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCurrentGameBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        if (savedInstanceState != null) {
            title = savedInstanceState.getString("TITLE");
        }

        TextView currentGameTextView = root.findViewById(R.id.currentGameTextView);
        currentGameTextView.setText(title);

        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("TITLE", title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}