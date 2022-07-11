package com.example.sharpshooter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.sharpshooter.ui.dashboard.DashboardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharpshooter.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Create FirebaseUtil Instance
        FirebaseUtil.getInstance();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);

        navView = binding.navView;
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                navController.popBackStack(item.getItemId(), false);

                switch (item.getItemId())
                {
                    case R.id.navigation_home:
                        navController.navigate(R.id.navigation_home);
                        return true;
                    case R.id.navigation_dashboard:
                        navController.navigate(R.id.navigation_dashboard);
                        return true;
                    case R.id.navigation_account:
                        navController.navigate(R.id.navigation_account);
                        return true;
                }

                return false;
            }
        });

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
    public static void replaceFragmentToHome(MainActivity view)
    {
        BottomNavigationView navigationView = view.findViewById(R.id.nav_view);
        navigationView.setSelectedItemId(R.id.navigation_home);
    }
}