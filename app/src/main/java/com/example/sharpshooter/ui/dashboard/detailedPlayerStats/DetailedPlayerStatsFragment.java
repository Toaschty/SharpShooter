package com.example.sharpshooter.ui.dashboard.detailedPlayerStats;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.Utils;
import com.example.sharpshooter.databinding.FragmentDetailedPlayerStatsBinding;
import com.example.sharpshooter.template.GameTemplate;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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
        if (FirebaseUtil.GetInstance() == null)
            return root;
        if (Utils.GetInstance() == null)
            return root;
        GameTemplate gameTemplate = FirebaseUtil.GetInstance().gameInstance;
        String playerNameBuffer = Utils.GetInstance().getBufferPlayerStats();
        playerName.setText(playerNameBuffer);
        points.setText(gameTemplate.getPlayerTotalScore(playerNameBuffer));
        Map<String, Integer> stats = Utils.GetInstance().generateStats(playerNameBuffer);

        shots.setText(String.valueOf(stats.get("shotsCount")));
        kills.setText(String.valueOf(stats.get("killsCount")));
        hits.setText(String.valueOf(stats.get("hitsCount")));
        misses.setText(String.valueOf(stats.get("missesCount")));

        // Calculate Kill ratio
        DecimalFormat df = new DecimalFormat("#.00");
        float killrate = (float) stats.get("killsCount") / (float) stats.get("shotsCount") * 100;
        killRate.setText(String.valueOf(df.format(killrate)) + " %");

        broken.setText(String.valueOf(stats.get("brokenCount")));

        btnClose.setOnClickListener(view -> {
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
