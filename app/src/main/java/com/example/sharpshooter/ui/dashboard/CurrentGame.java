package com.example.sharpshooter.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentCurrentGameStatsBinding;
import com.example.sharpshooter.databinding.FragmentCurrentGameStatsBinding;

import java.util.ArrayList;


public class CurrentGame extends Fragment {

    private String title;
    private FragmentCurrentGameStatsBinding binding;

    private RecyclerView card_PlayerstatRV;
    private ArrayList<CurrentGameCardModel> currentGameCardModelArrayList;

    public CurrentGame() {
    }

    public CurrentGame(String title) {
        this.title = title;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCurrentGameStatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        card_PlayerstatRV = root.findViewById(R.id.currentGameRecyclerView);
        currentGameCardModelArrayList = new ArrayList<>();
        currentGameCardModelArrayList.add(new CurrentGameCardModel("TestPlayer1", 10));

        CurrentGameCardAdapter currentGameCardAdapter = new CurrentGameCardAdapter(root.getContext(), currentGameCardModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);

        card_PlayerstatRV.setLayoutManager(linearLayoutManager);
        card_PlayerstatRV.setAdapter(currentGameCardAdapter);





        if (savedInstanceState != null) {
            title = savedInstanceState.getString("TITLE");
        }

        TextView currentGameTextView = root.findViewById(R.id.currentGameTitle);
        currentGameTextView.setText(title);


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