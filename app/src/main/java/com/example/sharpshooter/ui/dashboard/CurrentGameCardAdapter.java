package com.example.sharpshooter.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.R;
import com.google.errorprone.annotations.Var;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CurrentGameCardAdapter extends RecyclerView.Adapter<CurrentGameCardAdapter.Viewholder> {
    private Context context;
    private ArrayList<CurrentGameCardModel> currentGameCardModelArrayList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
        final Query docRef = db.collection("users").document(Objects.requireNonNull(mAuth.getUid())).collection("games").whereEqualTo("active", true);
        docRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@androidx.annotation.Nullable QuerySnapshot value, @androidx.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    System.out.println("Listen failed." + e);
                    return;
                }

                HashMap<String, HashMap<String ,Var >> player = null;

                for (QueryDocumentSnapshot doc : value){
                    if (doc.get("player") != null)
                    {
                        player = (HashMap<String, HashMap<String , Var>>) doc.get("player");
                    }
                }
                System.out.println(player.get("player").get("playerName"));
                System.out.println(player.get("player").get("totalScore"));
                System.out.println(player.get("player").get("targetScore"));
                ArrayList<Long> t = (ArrayList<Long>) player.get("player").get("targetScore");
                System.out.println(t.get(0));
                System.out.println(player);
                holder.score.setText(t.get(0).toString());

            }


        });
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
