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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.CourseAdapter;
import com.example.sharpshooter.CourseModel;
import com.example.sharpshooter.R;
import com.example.sharpshooter.databinding.FragmentDashboardBinding;
import com.example.sharpshooter.databinding.FragmentHomeBinding;
import com.example.sharpshooter.MainActivity;
import com.example.sharpshooter.ui.dashboard.DashboardFragment;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    private RecyclerView courseRV;

    private ArrayList<CourseModel> courseModelArrayList;



    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        // Card with Recycler view

        courseRV = root.findViewById(R.id.lastGameRecyclerView);

        // here we have created new array list and added data to it.
        courseModelArrayList = new ArrayList<>();
        courseModelArrayList.add(new CourseModel("DSA in Java", 4));
        courseModelArrayList.add(new CourseModel("Java Course", 3));
        courseModelArrayList.add(new CourseModel("C++ COurse", 4));
        courseModelArrayList.add(new CourseModel("DSA in C++", 4));
        courseModelArrayList.add(new CourseModel("Kotlin for Android", 4));
        courseModelArrayList.add(new CourseModel("Java for Android", 4));
        courseModelArrayList.add(new CourseModel("HTML and CSS", 4));

        // we are initializing our adapter class and passing our arraylist to it.
        CourseAdapter courseAdapter = new CourseAdapter(root.getContext(), courseModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(courseAdapter);



        final Button newGameBtn = binding.newGameBtn;
        homeViewModel.getBtnName().observe(getViewLifecycleOwner(), newGameBtn::setText);

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


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