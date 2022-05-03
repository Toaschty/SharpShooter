package com.example.sharpshooter.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private ViewPager2 currentGameViewPager;
    private ArrayList<CurrentGameModel> currentGameModelArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        currentGameViewPager = root.findViewById(R.id.currentGameViewPager);

        currentGameModelArrayList = new ArrayList<>();
        currentGameModelArrayList.add(new CurrentGameModel("TestPage1"));
        currentGameModelArrayList.add(new CurrentGameModel("TestPage2"));
        currentGameModelArrayList.add(new CurrentGameModel("TestPage3"));

        CurrentGameAdapter currentGameAdapter = new CurrentGameAdapter(this, currentGameModelArrayList);

        currentGameViewPager.setAdapter(currentGameAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }







}