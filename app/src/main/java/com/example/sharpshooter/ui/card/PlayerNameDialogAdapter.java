package com.example.sharpshooter.ui.card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.R;
import com.example.sharpshooter.ui.dashboard.CurrentGameCardAdapter;

public class PlayerNameDialogAdapter extends RecyclerView.Adapter<PlayerNameDialogAdapter.Viewholder> {
    private Context context;

    public PlayerNameDialogAdapter(Context context){
        this.context = context;

    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_playername, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class Viewholder extends RecyclerView.ViewHolder{


        public Viewholder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
