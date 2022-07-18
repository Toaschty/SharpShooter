package com.example.sharpshooter.ui.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.R;

import java.util.ArrayList;

public class PlayerNameDialogAdapter extends RecyclerView.Adapter<PlayerNameDialogAdapter.Viewholder> {
    private final Context context;
    private final ArrayList<PlayerNameDialogModel> playerNameDialogModelArrayList;

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
            holder.name.setText(FirebaseUtil.GetInstance().userInstance.getName());
            playerNameDialogModelArrayList.get(position).setName(FirebaseUtil.GetInstance().userInstance.getName());
            holder.name.setEnabled(false);
        }else {
            holder.name.setEnabled(true);
        }

        holder.playerName.setText("Player " + playerNameDialogModelArrayList.get(position).getCount());
    }

    public String getName(int i)
    {
        return playerNameDialogModelArrayList.get(i).getName();
    }

    @Override
    public int getItemCount() {
        return playerNameDialogModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        private final EditText name;
        private final TextView playerName;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.playerNameInput);
            name.setOnKeyListener ((view, keyEvent, i) -> {
                playerNameDialogModelArrayList.get(getAdapterPosition()).setName(name.getText().toString());
                return false;
            });

            playerName = itemView.findViewById(R.id.playerName);
        }
    }
}
