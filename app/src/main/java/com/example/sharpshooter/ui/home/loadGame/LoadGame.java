package com.example.sharpshooter.ui.home.loadGame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentLoadGameBinding;
import com.example.sharpshooter.ui.card.LastGameAdapter;
import com.example.sharpshooter.ui.card.LastGameModel;

import java.util.ArrayList;

public class LoadGame extends Fragment {
    private RecyclerView loadGameRV;

    private ArrayList<LastGameModel> lastGameModelArrayList;

    private Button btnClose;

    private FragmentLoadGameBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentLoadGameBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Card with Recycler view
        loadGameRV = root.findViewById(R.id.saved_game_RecyclerView);

        // dummy data.
        lastGameModelArrayList = new ArrayList<>();
        FirebaseUtil.GetInstance().getAllGames(value -> {
            if (value.getDocuments().size() > 0)
            {
                for (int i = 0; i < value.getDocuments().size(); i++) {
                    if (FirebaseUtil.GetInstance().userInstance.getSavedGameConfig().contains(value.getDocuments().get(i).getId())) {
                        ArrayList<Object> playerCount = (ArrayList<Object>) value.getDocuments().get(i).get("playerNames");
                        lastGameModelArrayList.add(new LastGameModel(value.getDocuments().get(i).get("gameName").toString(), (String) value.getDocuments().get(i).get("date"), playerCount.size(), Integer.parseInt(value.getDocuments().get(i).get("targetCount").toString()), R.drawable.ic_account_black_24dp, value.getDocuments().get(i).getId()));
                    }
                }
            }
            // we are initializing our adapter class and passing our arraylist to it.
            LastGameAdapter lastGameAdapter = new LastGameAdapter(root.getContext(), lastGameModelArrayList, "load");

            // below line is for setting a layout manager for our recycler view.
            // here we are creating vertical list so we will provide orientation as vertical
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);

            // in below two lines we are setting layoutmanager and adapter to our recycler view.
            loadGameRV.setLayoutManager(linearLayoutManager);
            loadGameRV.setAdapter(lastGameAdapter);
        });

        btnClose = binding.btnClose;

        btnClose.setOnClickListener(view -> Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.action_loadGame_to_navigation_home));

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
