package com.example.sharpshooter.ui.card;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.R;
import com.example.sharpshooter.ui.dashboard.CurrentGameCardAdapter;

import java.util.ArrayList;

public class PlayerNameDialogAdapter extends RecyclerView.Adapter<PlayerNameDialogAdapter.Viewholder> {
    private Context context;
    private ArrayList<PlayerNameDialogModel> playerNameDialogModelArrayList;

    public PlayerNameDialogAdapter(Context context, ArrayList<PlayerNameDialogModel> playerNameDialogModelArrayList){
        this.context = context;
        this.playerNameDialogModelArrayList = playerNameDialogModelArrayList;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_playername, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        if (playerNameDialogModelArrayList.get(position).isAccount()) {
            holder.name.setText(FirebaseUtil.getInstance().userInstance.getName());
            holder.name.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return playerNameDialogModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        private EditText name;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.playerNameInput);
        }
    }
}
