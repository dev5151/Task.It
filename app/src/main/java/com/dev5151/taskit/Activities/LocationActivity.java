package com.dev5151.taskit.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.dev5151.taskit.Fragments.LocationOptionFragment;
import com.dev5151.taskit.Fragments.MapFragment;
import com.dev5151.taskit.Interfaces.LocationInterface;
import com.dev5151.taskit.R;

public class LocationActivity extends AppCompatActivity {

    public static LocationInterface locationInterface;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        fragmentManager = getSupportFragmentManager();

        launchFragment(new LocationOptionFragment());

        locationInterface = new LocationInterface() {
            @Override
            public void switchToLocation() {
                launchFragment(new LocationOptionFragment());
            }

            @Override
            public void switchToMaps() {
                launchFragment(new MapFragment());
            }
        };

    }

    public static LocationInterface getLocationInterface() {
        return locationInterface;
    }

    private void launchFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, "").commit();
    }
}
