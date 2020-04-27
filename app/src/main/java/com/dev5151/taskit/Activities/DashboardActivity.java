package com.dev5151.taskit.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.dev5151.taskit.Fragments.AccountFragment;
import com.dev5151.taskit.Fragments.FetchTaskFragment;
import com.dev5151.taskit.Fragments.HomeFragment;
import com.dev5151.taskit.Fragments.PostTaskFragment;
import com.dev5151.taskit.Interfaces.BottomNavBarControlInterface;
import com.dev5151.taskit.Interfaces.FlowControlInterface;
import com.dev5151.taskit.R;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenu;

public class DashboardActivity extends AppCompatActivity {

    com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView;

    private static BottomNavBarControlInterface flowControlInterface;
    FragmentManager fragmentManager;
    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();

        flowControlInterface = new BottomNavBarControlInterface() {
            @Override
            public void launchHome() {
                launchFragment(new HomeFragment());
            }

            @Override
            public void launchAccount() {
                launchFragment(new AccountFragment());
            }

            @Override
            public void launchPostTask() {
                launchFragment(new PostTaskFragment());
            }

            @Override
            public void launchFetchTask() {
                launchFragment(new FetchTaskFragment());
            }

            @Override
            public void goToTask(int i, String taskId) {
                Intent intent = new Intent(DashboardActivity.this, TaskActivity.class);
                intent.putExtra("taskId", taskId);
                intent.putExtra("flag", i);
                startActivity(intent);

            }
        };

        getBottomNavBarControlInterface().launchHome();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    getBottomNavBarControlInterface().launchHome();
                    return true;
                case R.id.post:
                    getBottomNavBarControlInterface().launchPostTask();
                    return true;
                case R.id.search:
                    getBottomNavBarControlInterface().launchFetchTask();
                    return true;
                case R.id.account:
                    getBottomNavBarControlInterface().launchAccount();
                    return true;
            }

            return false;
        }
    };

    private void launchFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment, "").commit();
    }

    public static BottomNavBarControlInterface getBottomNavBarControlInterface() {
        return flowControlInterface;
    }

}

