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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentDashboardBinding;
import com.example.sharpshooter.databinding.FragmentHomeBinding;
import com.example.sharpshooter.MainActivity;
import com.example.sharpshooter.ui.dashboard.DashboardFragment;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button newGameBtn = binding.newGameBtn;
        homeViewModel.getBtnName().observe(getViewLifecycleOwner(), newGameBtn::setText);

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        final Button btnNewGameDialog = binding.newGameBtn;
        btnNewGameDialog.setOnClickListener(view -> showDialog());

        return root;
    }

    private void showDialog(){
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_newgame_dialog);
        Button startBtn = dialog.findViewById(R.id.startBtn);
        startBtn.setOnClickListener(view -> {
            MainActivity.setBottomNavVisibility(true, (MainActivity) getActivity());
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