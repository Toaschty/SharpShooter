package com.example.sharpshooter.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.R;

import java.util.ArrayList;

public class CurrentGameCardAdapter extends RecyclerView.Adapter<CurrentGameCardAdapter.Viewholder> {
    private Context context;
    private final ArrayList<CurrentGameCardModel> currentGameCardModelArrayList;
    private CurrentGameCardModel model;





    public CurrentGameCardAdapter(Context context, ArrayList<CurrentGameCardModel> currentGameCardModelArrayList){
        this.context = context;
        this.currentGameCardModelArrayList = currentGameCardModelArrayList;
    }


    @NonNull
    @Override
    public CurrentGameCardAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_playerstats, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentGameCardAdapter.Viewholder holder, @SuppressLint("RecyclerView") int position){
        model = currentGameCardModelArrayList.get(position);
        holder.playerName.setText(model.getPlayer_name());
        holder.score.setText(String.valueOf(FirebaseUtil.GetInstance().gameInstance.getPlayerTotalScore(model.getPlayer_name())));

        //a snapshot that updates
        FirebaseUtil.GetInstance().scoreListener(() -> {
            model = currentGameCardModelArrayList.get(position);
            holder.score.setText(String.valueOf(FirebaseUtil.GetInstance().gameInstance.getPlayerTotalScore(model.getPlayer_name())));
        });

        Log.i("Static getCurrentItem", String.valueOf(DashboardFragment.currentGameViewPager.getCurrentItem()));

        switch (FirebaseUtil.GetInstance().gameInstance.getPlayerBrokenArrowsWithId(model.getPlayer_name(), model.getTargetId()))
        {
            case 1: holder.brokenArrow1.setChecked(true); break;
            case 2: holder.brokenArrow1.setChecked(true); holder.brokenArrow2.setChecked(true); break;
            case 3: holder.brokenArrow1.setChecked(true); holder.brokenArrow2.setChecked(true); holder.brokenArrow3.setChecked(true); break;
        }

        switch (FirebaseUtil.GetInstance().gameInstance.getPlayerTargetScoreWithId(model.getPlayer_name(), model.getTargetId()))
        {
            case 20: holder.rg.check(R.id.currentGamePlayerPoint20); break;
            case 16: holder.rg.check(R.id.currentGamePlayerPoint16); break;
            case 14: holder.rg.check(R.id.currentGamePlayerPoint14); break;
            case 10: holder.rg.check(R.id.currentGamePlayerPoint10); break;
            case 8: holder.rg.check(R.id.currentGamePlayerPoint8); break;
            case 4: holder.rg.check(R.id.currentGamePlayerPoint4); break;
            case 0: holder.zeroButton.setChecked(true); break;
        }

    }

    @Override
    public int getItemCount() {
        return currentGameCardModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        private final TextView playerName;
        private final TextView score;
        private final RadioGroup rg;
        private final CheckBox zeroButton;
        private final CheckBox brokenArrow1;
        private final CheckBox brokenArrow2;
        private final CheckBox brokenArrow3;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerName);
            score = itemView.findViewById(R.id.currentGamePlayerPointSum);
            rg = itemView.findViewById(R.id.currentGamePlayerPoints);
            zeroButton = itemView.findViewById(R.id.currentGamePlayerPoint0);
            brokenArrow1 = itemView.findViewById(R.id.currentGamePlayerBrokenArrow1);
            brokenArrow2 = itemView.findViewById(R.id.currentGamePlayerBrokenArrow2);
            brokenArrow3 = itemView.findViewById(R.id.currentGamePlayerBrokenArrow3);

            FirebaseUtil.GetInstance().initGameInstanceWithId();

            brokenArrow1.setOnClickListener(view -> {
                int brokenArrows = 0;
                if (brokenArrow1.isChecked())
                    brokenArrows++;
                if (brokenArrow2.isChecked())
                    brokenArrows++;
                if (brokenArrow3.isChecked())
                    brokenArrows++;
                FirebaseUtil.GetInstance().gameInstance.setPlayerBrokenArrows(playerName.getText().toString(),model.getTargetId(), brokenArrows);
                FirebaseUtil.GetInstance().updateGameData("player", FirebaseUtil.GetInstance().gameInstance.getPlayer(), FirebaseUtil.GetInstance().activeGame);

            });
            brokenArrow2.setOnClickListener(view -> {
                int brokenArrows = 0;
                if (brokenArrow1.isChecked())
                    brokenArrows++;
                if (brokenArrow2.isChecked())
                    brokenArrows++;
                if (brokenArrow3.isChecked())
                    brokenArrows++;
                FirebaseUtil.GetInstance().gameInstance.setPlayerBrokenArrows(playerName.getText().toString(),model.getTargetId(), brokenArrows);
                FirebaseUtil.GetInstance().updateGameData("player", FirebaseUtil.GetInstance().gameInstance.getPlayer(), FirebaseUtil.GetInstance().activeGame);

            });
            brokenArrow3.setOnClickListener(view -> {
                int brokenArrows = 0;
                if (brokenArrow1.isChecked())
                    brokenArrows++;
                if (brokenArrow2.isChecked())
                    brokenArrows++;
                if (brokenArrow3.isChecked())
                    brokenArrows++;
                FirebaseUtil.GetInstance().gameInstance.setPlayerBrokenArrows(playerName.getText().toString(),model.getTargetId(), brokenArrows);
                FirebaseUtil.GetInstance().updateGameData("player", FirebaseUtil.GetInstance().gameInstance.getPlayer(), FirebaseUtil.GetInstance().activeGame);
            });

            rg.setOnCheckedChangeListener((radioGroup, i) -> {
                RadioButton radioButton = itemView.findViewById(i);
                if(radioButton != null){

                    FirebaseUtil.GetInstance().gameInstance.setPlayerTargetScore(playerName.getText().toString(),model.getTargetId(),  Integer.parseInt(radioButton.getText().toString()));
                    int totalScore = 0;
                    for (int j = 0; j < FirebaseUtil.GetInstance().gameInstance.getPlayerTargetScore(playerName.getText().toString()).size(); j++) {
                        if (FirebaseUtil.GetInstance().gameInstance.getPlayerTargetScore(playerName.getText().toString()).get(j) <= 20)
                            totalScore += FirebaseUtil.GetInstance().gameInstance.getPlayerTargetScore(playerName.getText().toString()).get(j);
                    }
                    FirebaseUtil.GetInstance().gameInstance.setPlayerTotalScore(playerName.getText().toString(), totalScore);
                    FirebaseUtil.GetInstance().updateGameData("player", FirebaseUtil.GetInstance().gameInstance.getPlayer(), FirebaseUtil.GetInstance().activeGame);

                    score.setText(FirebaseUtil.GetInstance().gameInstance.getPlayerTotalScore(playerName.getText().toString()));


                    if (zeroButton.isChecked()) {
                        zeroButton.toggle();
                    }

                }
            });

            zeroButton.setOnClickListener(view -> {
                rg.clearCheck();
                FirebaseUtil.GetInstance().gameInstance.setPlayerTargetScore(playerName.getText().toString(), model.getTargetId(),  0);
                int totalScore = 0;
                for (int j = 0; j < FirebaseUtil.GetInstance().gameInstance.getPlayerTargetScore(playerName.getText().toString()).size(); j++) {
                    if (FirebaseUtil.GetInstance().gameInstance.getPlayerTargetScore(playerName.getText().toString()).get(j) <= 20)
                        totalScore += FirebaseUtil.GetInstance().gameInstance.getPlayerTargetScore(playerName.getText().toString()).get(j);
                }
                FirebaseUtil.GetInstance().gameInstance.setPlayerTotalScore(playerName.getText().toString(), totalScore);
                FirebaseUtil.GetInstance().updateGameData("player", FirebaseUtil.GetInstance().gameInstance.getPlayer(), FirebaseUtil.GetInstance().activeGame);
                score.setText(String.valueOf(FirebaseUtil.GetInstance().gameInstance.getPlayerTotalScore(playerName.getText().toString())));


                zeroButton.setChecked(true);
            });


        }
    }


}
