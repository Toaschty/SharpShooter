package com.example.sharpshooter.ui.card;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.ImageLoader;
import com.example.sharpshooter.R;
import com.example.sharpshooter.Utils;
import com.example.sharpshooter.ui.NewParkourImageDialog;
import com.example.sharpshooter.ui.PlayerInputDialog;

import java.util.ArrayList;
import java.util.Objects;

public class LastGameAdapter extends RecyclerView.Adapter<LastGameAdapter.Viewholder> {

    private final Context context;
    private final ArrayList<LastGameModel> lastGameModelArrayList;
    private final String call;

    public LastGameAdapter(Context context, ArrayList<LastGameModel> lastGameModelArrayList, String call) {
        this.context = context;
        this.lastGameModelArrayList = lastGameModelArrayList;
        this.call = call;
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

        // Set default image -> Then try to load image from database
        holder.lastGameIV.setImageResource(model.getLastGame_image());
        ImageLoader.GetInstance().addImageViewToLoading(holder.lastGameIV, model.getGameId());
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
        private final TextView lastGameName;
        private final TextView lastGameDate;
        private final TextView lastGamePlayerCount;
        private final TextView lastGameTargetCount;
        private final ImageView lastGameIV;


        @SuppressLint("ClickableViewAccessibility")
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            lastGameName = itemView.findViewById(R.id.idLastGameName);
            lastGameDate = itemView.findViewById(R.id.idLastGameDate);
            lastGamePlayerCount = itemView.findViewById(R.id.idLastGamePlayerCount);
            lastGameTargetCount = itemView.findViewById(R.id.idLastGameTargetCount);
            lastGameIV = itemView.findViewById(R.id.idLastGameImage);
            CardView cv = (CardView) itemView.findViewById(R.id.lastGameCV);

            cv.setOnClickListener((view) -> {
                if(Objects.equals(call, "game")) {
                    FirebaseUtil.GetInstance().setActiveGame(lastGameModelArrayList.get(getAdapterPosition()).getGameId());
                    FirebaseUtil.GetInstance().initGameInstanceWithId(() -> {
                        if (Utils.GetInstance() == null)
                            return;
                        Utils.GetInstance().setBottomNavVisibility(true);
                        Utils.GetInstance().replaceFragment();
                    });
                } else if(Objects.equals(call, "load"))
                {
                    // Setup dialog
                    PlayerInputDialog playerInputDialog = new PlayerInputDialog(R.layout.dialog_newparkour_playernames, view);
                    NewParkourImageDialog playerImageDialog = new NewParkourImageDialog(R.layout.dialog_newparkour_image, playerInputDialog);

                    // Load and set data from saved parkour
                    playerImageDialog.setData(lastGameName.getText().toString(), Math.toIntExact(Long.parseLong(lastGamePlayerCount.getText().toString())), Math.toIntExact(Long.parseLong(lastGameTargetCount.getText().toString())));

                    // Show dialog
                    playerImageDialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "playerInputDialog");
                }
            });

            // Start game name marquee
            cv.setOnTouchListener((v, event) -> {
                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN: lastGameName.setSelected(true); break;
                    case MotionEvent.ACTION_CANCEL: lastGameName.setSelected(false); break;
                }
                return false;
            });
        }
    }
}