package com.example.sharpshooter;

import android.content.Intent;
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

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Create FirebaseUtil Instance
        FirebaseUtil.getInstance();

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