package com.example.sharpshooter.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.ui.NewGameDialog;
import com.example.sharpshooter.ui.NewParkourDialog;
import com.example.sharpshooter.ui.PlayerInputDialog;
import com.example.sharpshooter.ui.card.LastGameAdapter;
import com.example.sharpshooter.ui.card.LastGameModel;
import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentHomeBinding;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private RecyclerView lastGameRV;
    private RecyclerView activeGameRV;

    private ArrayList<LastGameModel> lastGameModelArrayList;
    private ArrayList<LastGameModel> activeGameModelArrayList;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Card with Recycler view
        lastGameRV = root.findViewById(R.id.lastGameRecyclerView);
        activeGameRV = root.findViewById(R.id.activeGameRecyclerView);

        // dummy data.
        lastGameModelArrayList = new ArrayList<>();
        activeGameModelArrayList = new ArrayList<>();

        FirebaseUtil.getInstance().database.collection("users").document(FirebaseUtil.getInstance().authentication.getUid()).collection("games").addSnapshotListener((value, error) -> {

            if (value.getDocuments().size() > 0)
            {
                for (int i = 0; i < value.getDocuments().size(); i++) {
                    if (value.getDocuments().get(i).get("active").toString() == "true")
                        activeGameModelArrayList.add(0,new LastGameModel("ActiveGame", "01.01.2022", 3, Integer.parseInt(value.getDocuments().get(i).get("targetCount").toString()), R.drawable.ic_account_black_24dp));
                    else
                        lastGameModelArrayList.add(new LastGameModel(value.getDocuments().get(i).get("gameName").toString(), "01.01.2022", 3, Integer.parseInt(value.getDocuments().get(i).get("targetCount").toString()), R.drawable.ic_account_black_24dp));
                }
            }

            // we are initializing our adapter class and passing our arraylist to it.
            LastGameAdapter lastGameAdapter = new LastGameAdapter(root.getContext(), lastGameModelArrayList);
            LastGameAdapter activeGameAdapter = new LastGameAdapter(root.getContext(), activeGameModelArrayList);

            // below line is for setting a layout manager for our recycler view.
            // here we are creating vertical list so we will provide orientation as vertical
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);

            // in below two lines we are setting layoutmanager and adapter to our recycler view.
            lastGameRV.setLayoutManager(linearLayoutManager);
            lastGameRV.setAdapter(lastGameAdapter);

            activeGameRV.setLayoutManager(linearLayoutManager2);
            activeGameRV.setAdapter(activeGameAdapter);
        });


        final Button btnNewGameDialog = binding.btnStartGame;
        btnNewGameDialog.setOnClickListener(viewDialog -> showDialog());

        return root;
    }

    private void showDialog(){
        PlayerInputDialog playerInputDialog = new PlayerInputDialog(R.layout.dialog_newparkour_playernames);
        NewParkourDialog newParkourDialog = new NewParkourDialog(R.layout.dialog_newparkour, playerInputDialog);
        NewGameDialog newGameDialog = new NewGameDialog(R.layout.dialog_newgame, newParkourDialog);

        newGameDialog.show(getParentFragmentManager(), "newGameDialog");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}