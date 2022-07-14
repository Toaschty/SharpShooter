package com.example.sharpshooter.ui.account.savedGames;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentAccountSavedGamesBinding;
import com.example.sharpshooter.ui.card.LastGameAdapter;
import com.example.sharpshooter.ui.card.LastGameModel;

import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings("ALL")
public class SavedGameFragment extends Fragment {
    private RecyclerView playedGamesRV;

    private ArrayList<LastGameModel> lastGameModelArrayList;

    private FragmentAccountSavedGamesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentAccountSavedGamesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Card with Recycler view
        playedGamesRV = root.findViewById(R.id.saved_games_RecyclerView);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(playedGamesRV);

        lastGameModelArrayList = new ArrayList<>();
        FirebaseUtil.GetInstance().getAllGames(value -> {
            if (value.getDocuments().size() > 0)
            {
                for (int i = 0; i < value.getDocuments().size(); i++) {
                    if (FirebaseUtil.GetInstance().userInstance.getSavedGameConfig().contains(value.getDocuments().get(i).getId())) {
                        ArrayList<Object> playerCount = (ArrayList<Object>) value.getDocuments().get(i).get("playerNames");
                        lastGameModelArrayList.add(new LastGameModel(Objects.requireNonNull(value.getDocuments().get(i).get("gameName")).toString(), (String) value.getDocuments().get(i).get("date"), Objects.requireNonNull(playerCount).size(), Integer.parseInt(Objects.requireNonNull(value.getDocuments().get(i).get("targetCount")).toString()), value.getDocuments().get(i).getId()));
                    }
                }
            }
            // we are initializing our adapter class and passing our arraylist to it.
            LastGameAdapter lastGameAdapter = new LastGameAdapter(root.getContext(), lastGameModelArrayList, "load");

            // below line is for setting a layout manager for our recycler view.
            // here we are creating vertical list so we will provide orientation as vertical
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);

            // in below two lines we are setting layoutmanager and adapter to our recycler view.
            playedGamesRV.setLayoutManager(linearLayoutManager);
            playedGamesRV.setAdapter(lastGameAdapter);
        });

        // Setup close button
        Button btn_close = (Button) binding.btnClose;
        btn_close.setOnClickListener(click -> Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.nav_host_fragment_activity_main).navigate(R.id.action_savedGameFragment_to_navigation_account));

        return root;
    }

    // Swipe to delete callback
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            // Delete swiped game from database
            int pos = viewHolder.getAdapterPosition();
            LastGameModel model = lastGameModelArrayList.get(pos);
            FirebaseUtil.GetInstance().deleteGame(model.getGameId());
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

