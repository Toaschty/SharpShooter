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

import java.util.ArrayList;
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
        GameTemplate gameTemplate = FirebaseUtil.GetInstance().gameInstance;
        String playerNameBuffer = Utils.GetInstance().getBufferPlayerStats();
        playerName.setText(playerNameBuffer);
        points.setText(gameTemplate.getPlayerTotalScore(playerNameBuffer));
        ArrayList<Long> targetScore = gameTemplate.getPlayerTargetScore(playerNameBuffer);

        int targetCount = gameTemplate.getTargetCount();
        AtomicInteger shotsCount = new AtomicInteger();
        AtomicInteger killsCount = new AtomicInteger();
        AtomicInteger missesCount = new AtomicInteger();
        AtomicInteger hitsCount = new AtomicInteger();
        int brokenCount = 0;

        for (int i = 0; i < targetCount; i++) {
            brokenCount += gameTemplate.getPlayerBrokenArrowsWithId(playerNameBuffer, i);
        }

        targetScore.forEach(n -> {
            if ( n == 20 )
            {
                killsCount.getAndIncrement();
                shotsCount.getAndIncrement();
                hitsCount.getAndIncrement();
            }else if( n == 16 )
            {
                shotsCount.getAndIncrement();
                hitsCount.getAndIncrement();
            }
            else if( n == 14 )
            {
                killsCount.getAndIncrement();
                shotsCount.addAndGet(2);
                hitsCount.getAndIncrement();
                missesCount.getAndIncrement();
            }
            else if ( n == 10 ) {
                shotsCount.addAndGet(2);
                hitsCount.getAndIncrement();
                missesCount.getAndIncrement();
            }
            else if (n == 8) {
                killsCount.getAndIncrement();
                shotsCount.addAndGet(3);
                hitsCount.getAndIncrement();
                missesCount.addAndGet(2);
            }
            else if (n == 4) {
                shotsCount.addAndGet(3);
                hitsCount.getAndIncrement();
                missesCount.addAndGet(2);
            }
            else {
                shotsCount.addAndGet(3);
                missesCount.addAndGet(3);
            }

        });

        shots.setText(String.valueOf(shotsCount));
        kills.setText(String.valueOf(killsCount));
        hits.setText(String.valueOf(hitsCount));
        misses.setText(String.valueOf(missesCount));
        killRate.setText(String.valueOf(((float) killsCount.get()) / (float) shotsCount.get()));
        broken.setText(String.valueOf(brokenCount));

        btnClose.setOnClickListener(view -> {
            getActivity().onBackPressed();
        });
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
