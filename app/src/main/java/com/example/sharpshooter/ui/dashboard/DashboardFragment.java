package com.example.sharpshooter.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentCurrentGameSliderBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;

public class DashboardFragment extends Fragment {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private FragmentCurrentGameSliderBinding binding;
    public static ViewPager2 currentGameViewPager;
    private ArrayList<CurrentGameModel> currentGameModelArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCurrentGameSliderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        currentGameModelArrayList = new ArrayList<>();

        currentGameViewPager = root.findViewById(R.id.currentGameViewPager);

        for (int i = 0; i < FirebaseUtil.GetInstance().gameInstance.getTargetCount(); i++) {
            currentGameModelArrayList.add(new CurrentGameModel("Target " + (i + 1), i));
        }
        assert getParentFragment() != null;
        CurrentGameAdapter currentGameAdapter = new CurrentGameAdapter(getParentFragment(), currentGameModelArrayList);
        currentGameViewPager.setAdapter(currentGameAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}