package com.example.sharpshooter.ui.dashboard;

import android.content.Context;
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

import com.example.sharpshooter.R;

import java.util.ArrayList;

public class CurrentGameCardAdapter extends RecyclerView.Adapter<CurrentGameCardAdapter.Viewholder> {
    private Context context;
    private ArrayList<CurrentGameCardModel> currentGameCardModelArrayList;

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
    public void onBindViewHolder(@NonNull CurrentGameCardAdapter.Viewholder holder, int position) {
            CurrentGameCardModel model = currentGameCardModelArrayList.get(position);
            holder.playername.setText(model.getPlayer_name());
            holder.score.setText(Integer.toString(model.getScore()));
    }

    @Override
    public int getItemCount() {
        return currentGameCardModelArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        private TextView playername;
        private TextView score;
        private RadioGroup rg;
        private CheckBox zeroButton;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            playername = itemView.findViewById(R.id.playerName);
            score = itemView.findViewById(R.id.currentGamePlayerPointSum);
            rg = (RadioGroup) itemView.findViewById(R.id.currentGamePlayerPoints);
            zeroButton = (CheckBox) itemView.findViewById(R.id.currentGamePlayerPoint0);




            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    RadioButton radioButton = itemView.findViewById(i);
                    if(radioButton != null){
                        score.setText(radioButton.getText());

                        final CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.currentGamePlayerPoint0);
                        if (checkBox.isChecked()) {
                            checkBox.toggle();
                        }

                    }
                }
            });

            zeroButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RadioGroup radioGroup = itemView.findViewById(R.id.currentGamePlayerPoints);
                    radioGroup.clearCheck();
                    zeroButton.setChecked(true);

                }
            });


        }
    }


}
