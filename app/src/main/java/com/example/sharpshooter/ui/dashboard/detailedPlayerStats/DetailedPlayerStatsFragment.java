package com.example.sharpshooter.ui.dashboard.detailedPlayerStats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.R;
import com.example.sharpshooter.Utils;
import com.example.sharpshooter.databinding.FragmentDetailedPlayerStatsBinding;
import com.example.sharpshooter.template.GameTemplate;

public class DetailedPlayerStatsFragment extends Fragment {
    private FragmentDetailedPlayerStatsBinding binding;

    private TextView playerName;
    private TextView points;
    private TextView shots;
    private TextView kills;
    private TextView hits;
    private TextView misses;
    private TextView killRate;
    private TextView broken;

    private Button btnClose;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDetailedPlayerStatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Get references
        playerName = binding.playerName;
        points = binding.points;
        shots = binding.shots;
        kills = binding.kills;
        hits = binding.hits;
        misses = binding.misses;
        killRate = binding.killRate;
        broken = binding.broken;

        btnClose = binding.btnDone;
        GameTemplate gameTemplate = FirebaseUtil.GetInstance().gameInstance;
        String playerNameBuffer = Utils.GetInstance().getBufferPlayerStats();
        playerName.setText(playerNameBuffer);
        points.setText(gameTemplate.getPlayerTotalScore(playerNameBuffer));
        shots.setText("0");
        kills.setText("0");
        hits.setText("0");
        misses.setText("0");
        killRate.setText("0");
        broken.setText("0");

        btnClose.setOnClickListener(view -> {
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
