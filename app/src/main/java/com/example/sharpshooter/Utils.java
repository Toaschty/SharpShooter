package com.example.sharpshooter;

import android.graphics.drawable.Icon;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Utils
{
    private static Utils _instance;

    private MainActivity context;
    private final ImageView loadingIndicator;
    private final RotateAnimation loadingRotate;
    private String playerName;



    public static void SetupUtils(MainActivity activity)
    {
        _instance = new Utils(activity);
    }

    public static Utils GetInstance()
    {
        if (_instance != null)
            return _instance;
        return null;
    }

    private Utils(MainActivity activity)
    {
        context = activity;

        // Get loading indicator reference
        loadingIndicator = activity.findViewById(R.id.loadingIndicator);

        // Setup loading animation
        loadingRotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.45f, Animation.RELATIVE_TO_SELF, 0.55f);
        loadingRotate.setDuration(2000);
        loadingRotate.setRepeatCount(Animation.INFINITE);
    }

    public void StartLoading()
    {
        loadingIndicator.setAnimation(loadingRotate);
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    public void StopLoading()
    {
        loadingIndicator.setVisibility(View.INVISIBLE);
        loadingRotate.cancel();
        loadingRotate.reset();
    }

    public void HandleLoadingProgress(int progress)
    {
        // If progress reaches 2 => All data is loaded
        if (progress == 2)
            StopLoading();
    }

    public void setAccountNav(boolean enable)
    {
        BottomNavigationView navView = context.findViewById(R.id.nav_view);
        navView.getMenu().findItem(R.id.navigation_account).setEnabled(enable);
    }

    public void setBottomNavVisibility(boolean visibility)
    {
        BottomNavigationView navView = context.findViewById(R.id.nav_view);
        navView.getMenu().findItem(R.id.navigation_dashboard).setVisible(visibility);
    }

    public void replaceFragment()
    {
        BottomNavigationView navigationView = context.findViewById(R.id.nav_view);
        navigationView.setSelectedItemId(R.id.navigation_dashboard);
    }
    public void replaceFragmentToHome()
    {
        BottomNavigationView navigationView = context.findViewById(R.id.nav_view);
        navigationView.setSelectedItemId(R.id.navigation_home);
    }

    public void setBufferPlayerStats(String playerName)
    {
        this.playerName = playerName;
    }

    public String getBufferPlayerStats(){
        return playerName;
    }

}
