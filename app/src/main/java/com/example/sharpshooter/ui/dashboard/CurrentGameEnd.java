package com.example.sharpshooter.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.R;
import com.example.sharpshooter.Utils;
import com.example.sharpshooter.databinding.FragmentCurrentGameEndBinding;
import com.example.sharpshooter.ui.card.CurrentGameWinAdapter;
import com.example.sharpshooter.ui.card.CurrentGameWinModel;

import java.util.ArrayList;

public class CurrentGameEnd extends Fragment {
    private FragmentCurrentGameEndBinding binding;
    private RecyclerView card_open_player_stats;
    private ArrayList<CurrentGameWinModel> currentGameModelArrayList;

    public CurrentGameEnd() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrentGameEndBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        card_open_player_stats = root.findViewById(R.id.currentGameStatsButtons);
        currentGameModelArrayList = new ArrayList<>();
        for (int i = 0; i < FirebaseUtil.GetInstance().gameInstance.getPlayerNames().size(); i++) {
            String playerName = FirebaseUtil.GetInstance().gameInstance.getPlayerNames().get(i);
            currentGameModelArrayList.add(new CurrentGameWinModel(playerName));
        }

        CurrentGameWinAdapter currentGameWinAdapter = new CurrentGameWinAdapter(root.getContext(), currentGameModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
        card_open_player_stats.setLayoutManager(linearLayoutManager);
        card_open_player_stats.setAdapter(currentGameWinAdapter);

        final Button btnDone = binding.btnDone;
        btnDone.setOnClickListener(btn -> {
            if (FirebaseUtil.GetInstance().gameInstance != null) {
                FirebaseUtil.GetInstance().gameInstance.setActive(false);
                FirebaseUtil.GetInstance().updateGameData("active", FirebaseUtil.GetInstance().gameInstance.isActive(), FirebaseUtil.GetInstance().activeGame);
            }
            Utils.GetInstance().setBottomNavVisibility(false);
            Utils.GetInstance().replaceFragmentToHome();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}