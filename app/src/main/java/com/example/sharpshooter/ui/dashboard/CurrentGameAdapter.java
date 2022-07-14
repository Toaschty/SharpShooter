package com.example.sharpshooter.ui.dashboard;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import java.util.ArrayList;


public class CurrentGameAdapter extends FragmentStateAdapter {

    private final ArrayList<CurrentGameModel> currentGameModelArrayList;

    public CurrentGameAdapter(@NonNull Fragment fragment, ArrayList<CurrentGameModel> currentGameModelArrayList) {
        super(fragment);
        this.currentGameModelArrayList = currentGameModelArrayList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == getItemCount()-1) {
            return new CurrentGameEnd();
        } else if (position == getItemCount()-2) {
            return new CurrentGameWin();
        } else {
            return new CurrentGame(currentGameModelArrayList.get(position).getCurrentGame_name(), position);
        }
    }

    @Override
    public int getItemCount() {
        return currentGameModelArrayList.size() + 2;
    }
}
