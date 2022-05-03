package com.example.sharpshooter.ui.dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sharpshooter.R;


public class CurrentGame extends Fragment {

    private String title;

    public CurrentGame(String title) {
        this.title = title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_current_game, container, false);

        TextView currentGameTextView = root.findViewById(R.id.currentGameTextView);
        currentGameTextView.setText(title);

        // Inflate the layout for this fragment
        return root;
    }
}