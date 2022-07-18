package com.example.sharpshooter.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentCurrentGameWinBinding;
import com.example.sharpshooter.ui.card.LeaderboardCardAdapter;
import com.example.sharpshooter.ui.card.LeaderboardModel;

import java.util.ArrayList;
import java.util.Comparator;


public class CurrentGameWin extends Fragment {
    private FragmentCurrentGameWinBinding binding;
    private RecyclerView card_Leaderboard;
    private ArrayList<LeaderboardModel> leaderboardModelArrayList;

    public CurrentGameWin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrentGameWinBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        FirebaseUtil.GetInstance().scoreListener(() -> {

            card_Leaderboard = root.findViewById(R.id.currentGameLeaderboardRecyclerView);
            leaderboardModelArrayList = new ArrayList<>();
            for (int i = 0; i < FirebaseUtil.GetInstance().gameInstance.getPlayerNames().size(); i++) {
                String playerName = FirebaseUtil.GetInstance().gameInstance.getPlayerNames().get(i);
                leaderboardModelArrayList.add(new LeaderboardModel(playerName, Integer.parseInt(FirebaseUtil.GetInstance().gameInstance.getPlayerTotalScore(playerName)), (FirebaseUtil.GetInstance().gameInstance.getTargetCount()*20)));
            }
            leaderboardModelArrayList.sort(Comparator.comparing(LeaderboardModel::getPoints).reversed());
            leaderboardModelArrayList.get(0).setPlayerName(leaderboardModelArrayList.get(0).getPlayerName() + " - Winner");

            LeaderboardCardAdapter leaderboardCardAdapter = new LeaderboardCardAdapter(root.getContext(), leaderboardModelArrayList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);

            card_Leaderboard.setLayoutManager(linearLayoutManager);
            card_Leaderboard.setAdapter(leaderboardCardAdapter);
            });

        // Setup help button
        Button helpButton = binding.currentGameHelp;
        helpButton.setOnClickListener(click -> Navigation.findNavController(root).navigate(R.id.action_navigation_dashboard_to_helpLeaderboardFragment));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


