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
import androidx.viewpager2.widget.ViewPager2;

import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentCurrentGameStatsBinding;
import com.example.sharpshooter.template.GameTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.errorprone.annotations.Var;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CurrentGameCardAdapter extends RecyclerView.Adapter<CurrentGameCardAdapter.Viewholder> {
    private Context context;
    private ArrayList<CurrentGameCardModel> currentGameCardModelArrayList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private int targetId = 0;





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
        docRef.addSnapshotListener((value, e) -> {
            if (e != null) {
                System.out.println("Listen failed." + e);
                return;
            }

            HashMap<String, HashMap<String ,Object >> player = null;

            for (QueryDocumentSnapshot doc : value){
                if (doc.get("player") != null)
                {
                    player = (HashMap<String, HashMap<String , Object>>) doc.get("player");
                }
            }
            ArrayList<Long> targetScore = (ArrayList<Long>) player.get(model.getPlayer_name()).get("targetScore");
            //System.out.println(t.get(0));
            String totalScore = player.get(model.getPlayer_name()).get("totalScore").toString();

            holder.score.setText(totalScore);

            switch (Math.toIntExact(targetScore.get(targetId)))
            {
                case 20: holder.rg.check(R.id.currentGamePlayerPoint20); break;
                case 16: holder.rg.check(R.id.currentGamePlayerPoint16); break;
                case 14: holder.rg.check(R.id.currentGamePlayerPoint14); break;
                case 10: holder.rg.check(R.id.currentGamePlayerPoint10); break;
                case 8: holder.rg.check(R.id.currentGamePlayerPoint8); break;
                case 4: holder.rg.check(R.id.currentGamePlayerPoint4); break;
                case 0: holder.zeroButton.setChecked(true); break;
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
            rg = itemView.findViewById(R.id.currentGamePlayerPoints);
            zeroButton = itemView.findViewById(R.id.currentGamePlayerPoint0);




            rg.setOnCheckedChangeListener((radioGroup, i) -> {
                RadioButton radioButton = itemView.findViewById(i);
                if(radioButton != null){
                    System.out.println(db.collection("users").document(Objects.requireNonNull(mAuth.getUid())).collection("games").whereEqualTo("active", true).get().addOnCompleteListener(task -> {
                        DocumentSnapshot t = task.getResult().getDocuments().get(0);

                        db.collection("users").document(Objects.requireNonNull(mAuth.getUid()))
                                .collection("games").document(t.getId()).update("totalScore", FieldValue.increment(Integer.parseInt(radioButton.getText().toString())));
                    }));


                    final CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.currentGamePlayerPoint0);
                    if (checkBox.isChecked()) {
                        checkBox.toggle();
                    }

                }
            });

            zeroButton.setOnClickListener(view -> {
                RadioGroup radioGroup = itemView.findViewById(R.id.currentGamePlayerPoints);
                radioGroup.clearCheck();
                zeroButton.setChecked(true);

            });


        }
    }


}
