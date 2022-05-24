package com.example.sharpshooter.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.databinding.FragmentAccountStatisticsBinding;
import com.example.sharpshooter.template.UserTemplate;

public class AccountFragmentStatistics extends Fragment
{
    private FragmentAccountStatisticsBinding binding;

    private TextView games;
    private TextView points;
    private TextView shots;
    private TextView kills;
    private TextView hits;
    private TextView misses;
    private TextView killRate;
    private TextView broken;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAccountStatisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Get references
        games = binding.games;
        points = binding.points;
        shots = binding.shots;
        kills = binding.kills;
        hits = binding.hits;
        misses = binding.misses;
        killRate = binding.killRate;
        broken = binding.broken;

        // Get data from user object
        UserTemplate tmpUser = FirebaseUtil.getInstance().userInstance;
        games.setText(String.valueOf(tmpUser.getTotalGames()));
        points.setText(String.valueOf(tmpUser.getPoints()));
        shots.setText(String.valueOf(tmpUser.getShots()));
        kills.setText(String.valueOf(tmpUser.getKills()));
        hits.setText(String.valueOf(tmpUser.getHits()));
        misses.setText(String.valueOf(tmpUser.getMisses()));
        killRate.setText(String.valueOf(tmpUser.getKillRate()));
        broken.setText(String.valueOf(tmpUser.getBroken()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
