package com.example.sharpshooter;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.sharpshooter.template.GameTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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

    public Map<String, Integer> generateStats(String playerName)
    {
        GameTemplate gameTemplate = FirebaseUtil.GetInstance().gameInstance;
        Map<String, Integer> stats = new HashMap<>();
        int targetCount = gameTemplate.getTargetCount();
        AtomicInteger shotsCount = new AtomicInteger();
        AtomicInteger killsCount = new AtomicInteger();
        AtomicInteger missesCount = new AtomicInteger();
        AtomicInteger hitsCount = new AtomicInteger();
        int brokenCount = 0;
        ArrayList<Long> targetScore = gameTemplate.getPlayerTargetScore(playerName);
        for (int i = 0; i < targetCount; i++) {
            brokenCount += gameTemplate.getPlayerBrokenArrowsWithId(playerName, i);
        }
        targetScore.forEach(n -> {
            if ( n == 20 )
            {
                killsCount.getAndIncrement();
                shotsCount.getAndIncrement();
                hitsCount.getAndIncrement();
            }else if( n == 16 )
            {
                shotsCount.getAndIncrement();
                hitsCount.getAndIncrement();
            }
            else if( n == 14 )
            {
                killsCount.getAndIncrement();
                shotsCount.addAndGet(2);
                hitsCount.getAndIncrement();
                missesCount.getAndIncrement();
            }
            else if ( n == 10 ) {
                shotsCount.addAndGet(2);
                hitsCount.getAndIncrement();
                missesCount.getAndIncrement();
            }
            else if (n == 8) {
                killsCount.getAndIncrement();
                shotsCount.addAndGet(3);
                hitsCount.getAndIncrement();
                missesCount.addAndGet(2);
            }
            else if (n == 4) {
                shotsCount.addAndGet(3);
                hitsCount.getAndIncrement();
                missesCount.addAndGet(2);
            }
            else {
                shotsCount.addAndGet(3);
                missesCount.addAndGet(3);
            }

        });

        stats.put("targetCount", targetCount);
        stats.put("shotsCount", shotsCount.get());
        stats.put("killsCount", killsCount.get());
        stats.put("missesCount", missesCount.get());
        stats.put("hitsCount", hitsCount.get());
        stats.put("brokenCount", brokenCount);

        return stats;

    }

}
