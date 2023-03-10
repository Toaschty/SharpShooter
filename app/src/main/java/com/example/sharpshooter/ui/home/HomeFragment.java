package com.example.sharpshooter.ui.home;

import android.os.Bundle;
import android.util.Log;
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
import com.example.sharpshooter.ui.NewParkourImageDialog;
import com.example.sharpshooter.ui.PlayerInputDialog;
import com.example.sharpshooter.ui.card.LastGameAdapter;
import com.example.sharpshooter.ui.card.LastGameModel;
import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;


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
        root.findViewById(R.id.activeGameText).setVisibility(View.INVISIBLE);
        root.findViewById(R.id.activeGameHorizontalLine).setVisibility(View.INVISIBLE);

        // dummy data.
        lastGameModelArrayList = new ArrayList<>();
        activeGameModelArrayList = new ArrayList<>();

        FirebaseUtil.GetInstance().getAllGames(value -> {
            for (int i = 0; i < value.getDocuments().size(); i++) {
                @SuppressWarnings("unchecked") ArrayList<Object> playerCount = (ArrayList<Object>) value.getDocuments().get(i).get("playerNames");
                if (Objects.requireNonNull(value.getDocuments().get(i).get("active")).toString().equals("true"))
                    activeGameModelArrayList.add(0,new LastGameModel(Objects.requireNonNull(value.getDocuments().get(i).get("gameName")).toString(), (String) value.getDocuments().get(i).get("date"), Objects.requireNonNull(playerCount).size() , Integer.parseInt(Objects.requireNonNull(value.getDocuments().get(i).get("targetCount")).toString()), value.getDocuments().get(i).getId()));
                else
                    lastGameModelArrayList.add(new LastGameModel(Objects.requireNonNull(value.getDocuments().get(i).get("gameName")).toString(), (String) value.getDocuments().get(i).get("date"), Objects.requireNonNull(playerCount).size(), Integer.parseInt(Objects.requireNonNull(value.getDocuments().get(i).get("targetCount")).toString()), value.getDocuments().get(i).getId()));
            }

            lastGameModelArrayList.sort(Comparator.comparing(LastGameModel::getLastGame_date));

            if (lastGameModelArrayList.size() > 5)
            {
                int size = lastGameModelArrayList.size()-5;
                for (int i = 0; i < size; i++) {
                    lastGameModelArrayList.remove(5);
                }
            }

            if(activeGameModelArrayList.size() != 0)
            {
                root.findViewById(R.id.activeGameText).setVisibility(View.VISIBLE);
                root.findViewById(R.id.activeGameHorizontalLine).setVisibility(View.VISIBLE);
            }

            // we are initializing our adapter class and passing our arraylist to it.
            LastGameAdapter lastGameAdapter = new LastGameAdapter(root.getContext(), lastGameModelArrayList, "game");
            LastGameAdapter activeGameAdapter = new LastGameAdapter(root.getContext(), activeGameModelArrayList, "game");

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
        btnNewGameDialog.setOnClickListener(viewDialog -> showDialog(root));

        return root;
    }

    private void showDialog(View view){
        PlayerInputDialog playerInputDialog = new PlayerInputDialog(R.layout.dialog_newparkour_playernames, view);
        NewParkourImageDialog newParkourImageDialog = new NewParkourImageDialog(R.layout.dialog_newparkour_image, playerInputDialog);
        NewParkourDialog newParkourDialog = new NewParkourDialog(R.layout.dialog_newparkour, newParkourImageDialog);
        NewGameDialog newGameDialog = new NewGameDialog(R.layout.dialog_newgame, newParkourDialog);

        newGameDialog.show(getParentFragmentManager(), "newGameDialog");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}