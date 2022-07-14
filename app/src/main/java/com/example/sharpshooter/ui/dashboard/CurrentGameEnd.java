package com.example.sharpshooter.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.R;
import com.example.sharpshooter.Utils;
import com.example.sharpshooter.databinding.FragmentCurrentGameEndBinding;
import com.example.sharpshooter.template.UserTemplate;
import com.example.sharpshooter.ui.card.CurrentGameWinAdapter;
import com.example.sharpshooter.ui.card.CurrentGameWinModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CurrentGameEnd extends Fragment {
    private FragmentCurrentGameEndBinding binding;
    private RecyclerView card_open_player_stats;
    private ArrayList<CurrentGameWinModel> currentGameModelArrayList;
    private Button btnDone;
    private Button btnSaveGameConfig;

    public CurrentGameEnd() {
        // Required empty public constructor
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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

        btnSaveGameConfig = binding.btnSaveConfig;
        btnSaveGameConfig.setOnClickListener(btn -> {
            ArrayList<Object> gameConfig = FirebaseUtil.GetInstance().userInstance.getSavedGameConfig();
            if ( gameConfig.contains(FirebaseUtil.GetInstance().getActiveGame()) )
                return;
            gameConfig.add(FirebaseUtil.GetInstance().getActiveGame());
            FirebaseUtil.GetInstance().userInstance.setSavedGameConfig(gameConfig);
            FirebaseUtil.GetInstance().updateUserData("savedGameConfig", FirebaseUtil.GetInstance().userInstance.getSavedGameConfig());
        });


        btnDone = binding.btnDone;
        btnDone.setOnClickListener(btn -> {
            if (Utils.GetInstance() == null)
                return;
            if (FirebaseUtil.GetInstance().gameInstance != null) {
                FirebaseUtil.GetInstance().gameInstance.setActive(false);
                FirebaseUtil.GetInstance().updateGameData("active", FirebaseUtil.GetInstance().gameInstance.isActive(), FirebaseUtil.GetInstance().activeGame);
                UserTemplate uploadStats = FirebaseUtil.GetInstance().userInstance;

                Map<String, Integer> stats = Utils.GetInstance().generateStats(uploadStats.getName());
                uploadStats.addPoints(Integer.parseInt(FirebaseUtil.GetInstance().gameInstance.getPlayerTotalScore(uploadStats.getName())));
                uploadStats.addBroken(stats.get("brokenCount"));
                uploadStats.addHits(stats.get("hitsCount"));
                uploadStats.addKills(stats.get("killsCount"));
                uploadStats.addMisses(stats.get("missesCount"));
                uploadStats.addShots(stats.get("shotsCount"));
                uploadStats.addTotalGames();
                uploadStats.addKillRate();
                Map<String, Object> updateUserData = new HashMap<>();
                updateUserData.put("points",uploadStats.getPoints());
                updateUserData.put("broken",uploadStats.getBroken());
                updateUserData.put("hits",uploadStats.getHits());
                updateUserData.put("killRate",uploadStats.getKillRate());
                updateUserData.put("kills",uploadStats.getKills());
                updateUserData.put("misses",uploadStats.getMisses());
                updateUserData.put("shots",uploadStats.getShots());
                updateUserData.put("totalGames",uploadStats.getTotalGames());
                FirebaseUtil.GetInstance().updateMultipleUserDataFields(updateUserData);
            }
            Utils.GetInstance().setBottomNavVisibility(false);
            Utils.GetInstance().replaceFragmentToHome();
        });

        // Setup help button
        Button helpButton = binding.currentGameHelp;
        helpButton.setOnClickListener(click -> {
            Navigation.findNavController(root).navigate(R.id.action_navigation_dashboard_to_helpStatisticsFragment);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}