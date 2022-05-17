package com.example.sharpshooter.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentCurrentGameStatsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.errorprone.annotations.Var;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Objects;


public class CurrentGame extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private String title;
    private FragmentCurrentGameStatsBinding binding;

    private RecyclerView card_PlayerstatRV;
    private ArrayList<CurrentGameCardModel> currentGameCardModelArrayList;

    public CurrentGame() {
    }

    public CurrentGame(String title) {
        this.title = title;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCurrentGameStatsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        card_PlayerstatRV = root.findViewById(R.id.currentGameRecyclerView);
        currentGameCardModelArrayList = new ArrayList<>();
        db.collection("users").document(Objects.requireNonNull(mAuth.getUid())).collection("games").whereEqualTo("active", true).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        System.out.println(document.getId() + " => " + document.getData().get("playerNames"));
                        ArrayList<String> playerNames = (ArrayList<String>) document.getData().get("playerNames");
                        playerNames.forEach((n) -> currentGameCardModelArrayList.add(new CurrentGameCardModel(n, 0)));
                        CurrentGameCardAdapter currentGameCardAdapter = new CurrentGameCardAdapter(root.getContext(), currentGameCardModelArrayList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);

                        card_PlayerstatRV.setLayoutManager(linearLayoutManager);
                        card_PlayerstatRV.setAdapter(currentGameCardAdapter);

                    }


                } else {
                    System.out.println(task.getException());

                }
            }
        });









        if (savedInstanceState != null) {
            title = savedInstanceState.getString("TITLE");
        }

        TextView currentGameTextView = root.findViewById(R.id.currentGameTitle);
        currentGameTextView.setText(title);


        return root;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("TITLE", title);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}