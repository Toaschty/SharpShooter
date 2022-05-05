package com.example.sharpshooter.ui.dashboard;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import java.util.ArrayList;


public class CurrentGameAdapter extends FragmentStateAdapter {

    private ArrayList<CurrentGameModel> currentGameModelArrayList;


    public CurrentGameAdapter(@NonNull Fragment fragment, ArrayList<CurrentGameModel> currentGameModelArrayList) {
        super(fragment);
        this.currentGameModelArrayList = currentGameModelArrayList;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new CurrentGame(currentGameModelArrayList.get(position).getCurrentGame_name());
    }

    @Override
    public int getItemCount() {
        return currentGameModelArrayList.size();
    }
}
