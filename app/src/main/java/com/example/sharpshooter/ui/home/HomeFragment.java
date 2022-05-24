package com.example.sharpshooter.ui.home;

import android.app.Dialog;
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

    private ArrayList<LastGameModel> lastGameModelArrayList;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Card with Recycler view
        lastGameRV = root.findViewById(R.id.lastGameRecyclerView);

        // dummy data.
        lastGameModelArrayList = new ArrayList<>();
        FirebaseUtil.getInstance().database.collection("users").document(FirebaseUtil.getInstance().authentication.getUid()).collection("games").addSnapshotListener((value, error) -> {
            System.out.println(value.getDocuments().get(0).get("gameName"));
            System.out.println(value.getDocuments().get(0).get("targetCount"));
            System.out.println(value.getDocuments().size());
            for (int i = 0; i < value.getDocuments().size(); i++) {
                if (value.getDocuments().get(i).get("active").toString() == "true")
                    lastGameModelArrayList.add(0,new LastGameModel("ActiveGame", "01.01.2022", 3, Integer.parseInt(value.getDocuments().get(i).get("targetCount").toString()), R.drawable.ic_account_black_24dp));
                else
                    lastGameModelArrayList.add(new LastGameModel(value.getDocuments().get(i).get("gameName").toString(), "01.01.2022", 3, Integer.parseInt(value.getDocuments().get(i).get("targetCount").toString()), R.drawable.ic_account_black_24dp));
            }
            // we are initializing our adapter class and passing our arraylist to it.
            LastGameAdapter lastGameAdapter = new LastGameAdapter(root.getContext(), lastGameModelArrayList);

            // below line is for setting a layout manager for our recycler view.
            // here we are creating vertical list so we will provide orientation as vertical
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);

            // in below two lines we are setting layoutmanager and adapter to our recycler view.
            lastGameRV.setLayoutManager(linearLayoutManager);
            lastGameRV.setAdapter(lastGameAdapter);

        });


        final Button btnNewGameDialog = binding.btnStartGame;
        btnNewGameDialog.setOnClickListener(viewDialog -> showDialog());

        return root;
    }

    private void showDialog(){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_newgame);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
        Button startBtn = dialog.findViewById(R.id.newGame_newParkour);
        startBtn.setOnClickListener(view -> {
            Dialog newParkourDialog = new Dialog(getActivity());
            newParkourDialog.setContentView(R.layout.dialog_newparkour);
            newParkourDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);

            Button continueBtn = newParkourDialog.findViewById(R.id.newParkour_continue);
            continueBtn.setOnClickListener(view2 -> {
                Dialog newParkourPlayerNames = new Dialog(getActivity());
                newParkourPlayerNames.setContentView(R.layout.dialog_newparkour_playernames);
                newParkourPlayerNames.getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);

                newParkourPlayerNames.show();
/*
                playerNameDialogRV = view2.findViewById(R.id.recyclerViewPlayerNames);
                PlayerNameDialogAdapter playerNameDialogAdapter = new PlayerNameDialogAdapter(view2.getContext());
                System.out.println(playerNameDialogAdapter.getItemCount());
                playerNameDialogRV.setAdapter(playerNameDialogAdapter);

*/

                newParkourDialog.dismiss();
            });

            newParkourDialog.show();
            // MainActivity.setBottomNavVisibility(true, (MainActivity) getActivity());
            // MainActivity.replaceFragment( (MainActivity) getActivity());
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}