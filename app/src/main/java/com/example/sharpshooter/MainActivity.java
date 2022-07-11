package com.example.sharpshooter;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.sharpshooter.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup Utils
        Utils.SetupUtils(this);

        // Show loading indicator
        Utils.GetInstance().StartLoading();

        // Create FirebaseUtil Instance
        FirebaseUtil.GetInstance();

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
                        if (FirebaseUtil.GetInstance().loadingProgress < 2)
                            return false;
                        navController.navigate(R.id.navigation_account);
                        return true;
                }

                return false;
            }
        });

        Utils.GetInstance().setBottomNavVisibility(false);
    }
}