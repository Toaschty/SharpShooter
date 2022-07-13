package com.example.sharpshooter.ui.card;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.ImageLoader;
import com.example.sharpshooter.MainActivity;
import com.example.sharpshooter.R;
import com.example.sharpshooter.Utils;
import com.example.sharpshooter.ui.NewGameDialog;
import com.example.sharpshooter.ui.PlayerInputDialog;

import java.util.ArrayList;

public class LastGameAdapter extends RecyclerView.Adapter<LastGameAdapter.Viewholder> {

    private Context context;
    private ArrayList<LastGameModel> lastGameModelArrayList;
    private String call;

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
        private TextView lastGameName;
        private TextView lastGameDate;
        private TextView lastGamePlayerCount;
        private TextView lastGameTargetCount;
        private ImageView lastGameIV;

        private CardView cv;

        @SuppressLint("ClickableViewAccessibility")
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            lastGameName = itemView.findViewById(R.id.idLastGameName);
            lastGameDate = itemView.findViewById(R.id.idLastGameDate);
            lastGamePlayerCount = itemView.findViewById(R.id.idLastGamePlayerCount);
            lastGameTargetCount = itemView.findViewById(R.id.idLastGameTargetCount);
            lastGameIV = itemView.findViewById(R.id.idLastGameImage);
            cv = (CardView) itemView.findViewById(R.id.lastGameCV);

            cv.setOnClickListener((view) -> {
                if(call == "game") {
                    FirebaseUtil.GetInstance().setActiveGame(lastGameModelArrayList.get(getAdapterPosition()).getGameId());
                    FirebaseUtil.GetInstance().initGameInstanceWithId(() -> {
                        Utils.GetInstance().setBottomNavVisibility(true);
                        Utils.GetInstance().replaceFragment();
                    });
                }else if(call == "load")
                {
                    PlayerInputDialog playerInputDialog = new PlayerInputDialog(R.layout.dialog_newparkour_playernames, view, lastGameName.getText().toString(), Math.toIntExact(Long.parseLong(lastGamePlayerCount.getText().toString())), Math.toIntExact(Long.parseLong(lastGameTargetCount.getText().toString())));
                    playerInputDialog.show(((AppCompatActivity) context).getSupportFragmentManager(), "playerInputDialog");
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