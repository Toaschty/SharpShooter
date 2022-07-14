package com.example.sharpshooter.ui.account.playedGames;

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
import com.example.sharpshooter.databinding.FragmentAccountPlayedGamesBinding;
import com.example.sharpshooter.ui.card.LastGameAdapter;
import com.example.sharpshooter.ui.card.LastGameModel;

import java.util.ArrayList;
import java.util.Comparator;

public class PlayedGamesFragment extends Fragment {
    private RecyclerView playedGamesRV;

    private ArrayList<LastGameModel> lastGameModelArrayList;


    private FragmentAccountPlayedGamesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAccountPlayedGamesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Card with Recycler view
        playedGamesRV = root.findViewById(R.id.saved_games_RecyclerView);

        // dummy data.
        lastGameModelArrayList = new ArrayList<>();
        FirebaseUtil.GetInstance().getAllGames(value -> {
            if (value.getDocuments().size() > 0)
            {
                for (int i = 0; i < value.getDocuments().size(); i++) {
                    ArrayList<Object> playerCount = (ArrayList<Object>) value.getDocuments().get(i).get("playerNames");
                    lastGameModelArrayList.add(new LastGameModel(value.getDocuments().get(i).get("gameName").toString(), (String) value.getDocuments().get(i).get("date"), playerCount.size(), Integer.parseInt(value.getDocuments().get(i).get("targetCount").toString()), value.getDocuments().get(i).getId().toString()));
                }
            }
            lastGameModelArrayList.sort(Comparator.comparing(LastGameModel::getLastGame_date));

            // we are initializing our adapter class and passing our arraylist to it.
            LastGameAdapter lastGameAdapter = new LastGameAdapter(root.getContext(), lastGameModelArrayList, "game");

            // below line is for setting a layout manager for our recycler view.
            // here we are creating vertical list so we will provide orientation as vertical
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);

            // in below two lines we are setting layoutmanager and adapter to our recycler view.
            playedGamesRV.setLayoutManager(linearLayoutManager);
            playedGamesRV.setAdapter(lastGameAdapter);
        });

        // Setup close button
        Button btn_close = (Button) binding.btnClose;
        btn_close.setOnClickListener(click -> {
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main).navigate(R.id.action_playedGames_to_navigation_account);
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
