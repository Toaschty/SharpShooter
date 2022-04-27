package com.example.sharpshooter.ui.home;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.LastGameAdapter;
import com.example.sharpshooter.LastGameModel;
import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentHomeBinding;
import com.example.sharpshooter.MainActivity;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    private RecyclerView lastGameRV;

    private ArrayList<LastGameModel> lastGameModelArrayList;



    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        // Card with Recycler view

        lastGameRV = root.findViewById(R.id.lastGameRecyclerView);

        // dummy data.
        lastGameModelArrayList = new ArrayList<>();
        lastGameModelArrayList.add(new LastGameModel("Dummy Game1"));
        lastGameModelArrayList.add(new LastGameModel("Dummy Game2"));
        lastGameModelArrayList.add(new LastGameModel("Dummy Game3"));
        lastGameModelArrayList.add(new LastGameModel("Dummy Game4"));
        lastGameModelArrayList.add(new LastGameModel("Dummy Game5"));
        lastGameModelArrayList.add(new LastGameModel("Dummy Game6"));
        lastGameModelArrayList.add(new LastGameModel("Dummy Game7"));

        // we are initializing our adapter class and passing our arraylist to it.
        LastGameAdapter lastGameAdapter = new LastGameAdapter(root.getContext(), lastGameModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        lastGameRV.setLayoutManager(linearLayoutManager);
        lastGameRV.setAdapter(lastGameAdapter);



        final Button newGameBtn = binding.newGameBtn;
        homeViewModel.getBtnName().observe(getViewLifecycleOwner(), newGameBtn::setText);


        final Button btnNewGameDialog = binding.newGameBtn;
        btnNewGameDialog.setOnClickListener(viewDialog -> showDialog());

        return root;
    }

    private void showDialog(){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_newgame_dialog);
        Button startBtn = dialog.findViewById(R.id.startBtn);
        startBtn.setOnClickListener(view -> {
            MainActivity.setBottomNavVisibility(true, (MainActivity) getActivity());
            MainActivity.replaceFragment( (MainActivity) getActivity());
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