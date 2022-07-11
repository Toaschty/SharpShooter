package com.example.sharpshooter.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.R;
import com.example.sharpshooter.ScoreUpdater;
import com.example.sharpshooter.databinding.FragmentCurrentGameStatsBinding;
import com.example.sharpshooter.template.GameTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.errorprone.annotations.Var;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
        holder.score.setText(String.valueOf(FirebaseUtil.getInstance().gameInstance.getPlayerTotalScore(model.getPlayer_name())));

        //a snapshot that updates
        FirebaseUtil.getInstance().scoreListener(() -> {
            model = currentGameCardModelArrayList.get(position);
            holder.score.setText(String.valueOf(FirebaseUtil.getInstance().gameInstance.getPlayerTotalScore(model.getPlayer_name())));
        });

        Log.i("Static getCurrentItem", String.valueOf(DashboardFragment.currentGameViewPager.getCurrentItem()));

        switch (FirebaseUtil.getInstance().gameInstance.getPlayerBrokenArrowsWithId(model.getPlayer_name(), model.getTargetId()))
        {
            case 1: holder.brokenArrow1.setChecked(true); break;
            case 2: holder.brokenArrow1.setChecked(true); holder.brokenArrow2.setChecked(true); break;
            case 3: holder.brokenArrow1.setChecked(true); holder.brokenArrow2.setChecked(true); holder.brokenArrow3.setChecked(true); break;
        }

        switch (FirebaseUtil.getInstance().gameInstance.getPlayerTargetScoreWithId(model.getPlayer_name(), model.getTargetId()))
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

            FirebaseUtil.getInstance().initGameInstanceWithId();

            brokenArrow1.setOnClickListener(view -> {
                int brokenArrows = 0;
                if (brokenArrow1.isChecked())
                    brokenArrows++;
                if (brokenArrow2.isChecked())
                    brokenArrows++;
                if (brokenArrow3.isChecked())
                    brokenArrows++;
                FirebaseUtil.getInstance().gameInstance.setPlayerBrokenArrows(playerName.getText().toString(),model.getTargetId(), brokenArrows);
                FirebaseUtil.getInstance().updateGameData("player", FirebaseUtil.getInstance().gameInstance.getPlayer(), FirebaseUtil.getInstance().activeGame);

            });
            brokenArrow2.setOnClickListener(view -> {
                int brokenArrows = 0;
                if (brokenArrow1.isChecked())
                    brokenArrows++;
                if (brokenArrow2.isChecked())
                    brokenArrows++;
                if (brokenArrow3.isChecked())
                    brokenArrows++;
                FirebaseUtil.getInstance().gameInstance.setPlayerBrokenArrows(playerName.getText().toString(),model.getTargetId(), brokenArrows);
                FirebaseUtil.getInstance().updateGameData("player", FirebaseUtil.getInstance().gameInstance.getPlayer(), FirebaseUtil.getInstance().activeGame);

            });
            brokenArrow3.setOnClickListener(view -> {
                int brokenArrows = 0;
                if (brokenArrow1.isChecked())
                    brokenArrows++;
                if (brokenArrow2.isChecked())
                    brokenArrows++;
                if (brokenArrow3.isChecked())
                    brokenArrows++;
                FirebaseUtil.getInstance().gameInstance.setPlayerBrokenArrows(playerName.getText().toString(),model.getTargetId(), brokenArrows);
                FirebaseUtil.getInstance().updateGameData("player", FirebaseUtil.getInstance().gameInstance.getPlayer(), FirebaseUtil.getInstance().activeGame);
            });

            rg.setOnCheckedChangeListener((radioGroup, i) -> {
                RadioButton radioButton = itemView.findViewById(i);
                if(radioButton != null){

                    FirebaseUtil.getInstance().gameInstance.setPlayerTargetScore(playerName.getText().toString(),model.getTargetId(),  Integer.parseInt(radioButton.getText().toString()));
                    int totalScore = 0;
                    for (int j = 0; j < FirebaseUtil.getInstance().gameInstance.getPlayerTargetScore(playerName.getText().toString()).size(); j++) {
                        if (FirebaseUtil.getInstance().gameInstance.getPlayerTargetScore(playerName.getText().toString()).get(j) <= 20)
                            totalScore += FirebaseUtil.getInstance().gameInstance.getPlayerTargetScore(playerName.getText().toString()).get(j);
                    }
                    FirebaseUtil.getInstance().gameInstance.setPlayerTotalScore(playerName.getText().toString(), totalScore);
                    FirebaseUtil.getInstance().updateGameData("player", FirebaseUtil.getInstance().gameInstance.getPlayer(), FirebaseUtil.getInstance().activeGame);

                    score.setText(FirebaseUtil.getInstance().gameInstance.getPlayerTotalScore(playerName.getText().toString()));


                    if (zeroButton.isChecked()) {
                        zeroButton.toggle();
                    }

                }
            });
            zeroButton.setOnClickListener(view -> {
                rg.clearCheck();
                FirebaseUtil.getInstance().gameInstance.setPlayerTargetScore(playerName.getText().toString(), model.getTargetId(),  0);
                int totalScore = 0;
                for (int j = 0; j < FirebaseUtil.getInstance().gameInstance.getPlayerTargetScore(playerName.getText().toString()).size(); j++) {
                    if (FirebaseUtil.getInstance().gameInstance.getPlayerTargetScore(playerName.getText().toString()).get(j) <= 20)
                        totalScore += FirebaseUtil.getInstance().gameInstance.getPlayerTargetScore(playerName.getText().toString()).get(j);
                }
                FirebaseUtil.getInstance().gameInstance.setPlayerTotalScore(playerName.getText().toString(), totalScore);
                FirebaseUtil.getInstance().updateGameData("player", FirebaseUtil.getInstance().gameInstance.getPlayer(), FirebaseUtil.getInstance().activeGame);
                score.setText(String.valueOf(FirebaseUtil.getInstance().gameInstance.getPlayerTotalScore(playerName.getText().toString())));


                zeroButton.setChecked(true);
            });


        }
    }


}
