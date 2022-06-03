package com.example.sharpshooter.ui.card;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.MainActivity;
import com.example.sharpshooter.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class LastGameAdapter extends RecyclerView.Adapter<LastGameAdapter.Viewholder> {

    private Context context;
    private ArrayList<LastGameModel> lastGameModelArrayList;

    public LastGameAdapter(Context context, ArrayList<LastGameModel> lastGameModelArrayList) {
        this.context = context;
        this.lastGameModelArrayList = lastGameModelArrayList;
    }

    @NonNull
    @Override
    public LastGameAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_last_game, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LastGameAdapter.Viewholder holder, int position) {
        // to set data to textview of each card layout
        LastGameModel model = lastGameModelArrayList.get(position);
        holder.lastGameName.setText( model.getLastGame_name() );
        holder.lastGameDate.setText( model.getLastGame_date() );
        holder.lastGamePlayerCount.setText(String.valueOf( model.getLastGame_playerCount()) );
        holder.lastGameTargetCount.setText(String.valueOf( model.getLastGame_targetCount()) );
        holder.lastGameIV.setImageResource(model.getLastGame_image());

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return lastGameModelArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView.
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView lastGameName;
        private TextView lastGameDate;
        private TextView lastGamePlayerCount;
        private TextView lastGameTargetCount;
        private ImageView lastGameIV;


        private CardView cv;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            lastGameName = itemView.findViewById(R.id.idLastGameName);
            lastGameDate = itemView.findViewById(R.id.idLastGameDate);
            lastGamePlayerCount = itemView.findViewById(R.id.idLastGamePlayerCount);
            lastGameTargetCount = itemView.findViewById(R.id.idLastGameTargetCount);
            cv = (CardView) itemView.findViewById(R.id.lastGameCV);
            lastGameIV = itemView.findViewById(R.id.idLastGameImage);

            cv.setOnClickListener((view) -> {
                FirebaseUtil.getInstance().setActiveGame(lastGameModelArrayList.get(getAdapterPosition()).getGameId());
                FirebaseUtil.getInstance().initGameInstanceWithId();
                MainActivity.setBottomNavVisibility(true, (MainActivity) view.getContext());
                MainActivity.replaceFragment( (MainActivity) view.getContext());
            });

        }
    }
}