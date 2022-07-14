package com.example.sharpshooter.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentCurrentGameStatsBinding;
import com.example.sharpshooter.ui.help.HelpPointsFragment;

import java.util.ArrayList;


public class CurrentGame extends Fragment {

    private String title;
    private int targetId;
    private FragmentCurrentGameStatsBinding binding;

    private RecyclerView card_PlayerstatRV;
    private ArrayList<CurrentGameCardModel> currentGameCardModelArrayList;

    public CurrentGame() {
    }

    public CurrentGame(String title, int targetId) {
        this.title = title;
        this.targetId = targetId;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCurrentGameStatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        card_PlayerstatRV = root.findViewById(R.id.currentGameRecyclerView);
        currentGameCardModelArrayList = new ArrayList<>();
        for (int i = 0; i < FirebaseUtil.GetInstance().gameInstance.getPlayerNames().size(); i++) {
            currentGameCardModelArrayList.add(new CurrentGameCardModel(FirebaseUtil.GetInstance().gameInstance.getPlayerNames().get(i), 0, targetId));
        }

        CurrentGameCardAdapter currentGameCardAdapter = new CurrentGameCardAdapter(root.getContext(), currentGameCardModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);

        card_PlayerstatRV.setLayoutManager(linearLayoutManager);
        card_PlayerstatRV.setAdapter(currentGameCardAdapter);

        if (savedInstanceState != null) {
            title = savedInstanceState.getString("TITLE");
        }

        TextView currentGameTextView = root.findViewById(R.id.currentGameTitle);
        currentGameTextView.setText(title);

        // Setup help button
        Button helpButton = binding.currentGameHelp;
        helpButton.setOnClickListener(click -> {
            Navigation.findNavController(root).navigate(R.id.action_navigation_dashboard_to_helpPointsFragment);
        });

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