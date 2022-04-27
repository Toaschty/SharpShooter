package com.example.sharpshooter;

import android.os.Bundle;
import android.view.View;

import com.example.sharpshooter.ui.dashboard.DashboardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*
    private RecyclerView courseRV;

    private ArrayList<CourseModel> courseModelArrayList;
    */

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Card with Recycler view
        /*
        courseRV = findViewById(R.id.lastGameRecyclerView);

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
        CourseAdapter courseAdapter = new CourseAdapter(this, courseModelArrayList);

        // below line is for setting a layout manager for our recycler view.
        // here we are creating vertical list so we will provide orientation as vertical
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(courseAdapter);
        */


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);

        setBottomNavVisibility(false, this);


    }

    public static void setBottomNavVisibility(boolean visibility, MainActivity view)
    {
        BottomNavigationView navView = view.findViewById(R.id.nav_view);
        navView.getMenu().findItem(R.id.navigation_dashboard).setVisible(visibility);
    }

    public static void replaceFragment(MainActivity view)
    {
        BottomNavigationView navigationView = view.findViewById(R.id.nav_view);
        navigationView.setSelectedItemId(R.id.navigation_dashboard);
    }


}