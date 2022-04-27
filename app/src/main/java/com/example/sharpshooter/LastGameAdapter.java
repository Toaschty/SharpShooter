package com.example.sharpshooter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LastGameAdapter.Viewholder holder, int position) {
        // to set data to textview of each card layout
        LastGameModel model = lastGameModelArrayList.get(position);
        holder.lastGameName.setText(model.getLastGame_name());
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

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            lastGameName = itemView.findViewById(R.id.idLastGameName);
        }
    }
}