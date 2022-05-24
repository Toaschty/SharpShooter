package com.example.sharpshooter.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sharpshooter.FirebaseUtil;
import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentCurrentGameSliderBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.Objects;

public class DashboardFragment extends Fragment {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();


    private FragmentCurrentGameSliderBinding binding;
    public static ViewPager2 currentGameViewPager;
    private ArrayList<CurrentGameModel> currentGameModelArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentCurrentGameSliderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        currentGameModelArrayList = new ArrayList<>();

        currentGameViewPager = root.findViewById(R.id.currentGameViewPager);

        for (int i = 0; i < FirebaseUtil.getInstance().gameInstance.getTargetCount(); i++) {
            currentGameModelArrayList.add(new CurrentGameModel("Target" + i, i));
        }
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