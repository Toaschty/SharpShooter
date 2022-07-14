package com.example.sharpshooter.ui.card;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.MainActivity;
import com.example.sharpshooter.R;
import com.example.sharpshooter.Utils;
import com.example.sharpshooter.ui.dashboard.CurrentGameCardAdapter;

import java.util.ArrayList;

public class CurrentGameWinAdapter extends RecyclerView.Adapter<CurrentGameWinAdapter.Viewholder>{
    private final ArrayList<CurrentGameWinModel> currentGameWinModelArrayList;

    public CurrentGameWinAdapter(Context context, ArrayList<CurrentGameWinModel> currentGameWinModelArrayList){
        this.currentGameWinModelArrayList = currentGameWinModelArrayList;
    }
    @NonNull
    @Override
    public CurrentGameWinAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_open_player_stats, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentGameWinAdapter.Viewholder holder, int position) {
        // to set data to textview of each card layout
        CurrentGameWinModel model = currentGameWinModelArrayList.get(position);
        holder.btn_open_player_stats.setText(model.getPlayerName());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return currentGameWinModelArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView.
    public static class Viewholder extends RecyclerView.ViewHolder {
        private final Button btn_open_player_stats;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            btn_open_player_stats = itemView.findViewById(R.id.btn_open_player_stats);

            btn_open_player_stats.setOnClickListener((view) -> {
                if (Utils.GetInstance() != null)
                    Utils.GetInstance().setBufferPlayerStats(btn_open_player_stats.getText().toString());
                Navigation.findNavController(itemView).navigate(R.id.action_navigation_dashboard_to_detailedPlayerStatsFragment);
            });
        }
    }


}
