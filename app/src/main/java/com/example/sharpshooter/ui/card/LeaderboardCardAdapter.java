package com.example.sharpshooter.ui.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.MainActivity;
import com.example.sharpshooter.R;

import java.util.ArrayList;

public class LeaderboardCardAdapter extends RecyclerView.Adapter<LeaderboardCardAdapter.Viewholder> {
    private Context context;
    private ArrayList<LeaderboardModel> leaderboardModelArrayList;

    public LeaderboardCardAdapter(Context context, ArrayList<LeaderboardModel> leaderboardModelArrayList) {
        this.context = context;
        this.leaderboardModelArrayList = leaderboardModelArrayList;
    }

    @NonNull
    @Override
    public LeaderboardCardAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_leaderboard, parent, false);
        return new LeaderboardCardAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardCardAdapter.Viewholder holder, int position) {
        // to set data to textview of each card layout
        LeaderboardModel model = leaderboardModelArrayList.get(position);
        holder.playerName.setText( model.getPlayerName() );
        holder.progressBar.setProgress( model.getPoints() );
        holder.progressBar.setMax( model.getMaxPoints() );
        holder.points.setText(String.valueOf(model.getPoints()));
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return leaderboardModelArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView.
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView playerName;
        private ProgressBar progressBar;
        private TextView points;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerName);
            progressBar = itemView.findViewById(R.id.pointBar);
            points = itemView.findViewById(R.id.points);
        }
    }
}
