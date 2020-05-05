package com.dev5151.taskit.Fragments;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.dev5151.taskit.Interfaces.LocationInterface;
import com.dev5151.taskit.R;
import com.google.android.material.button.MaterialButton;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;

public class LocationFragment extends Fragment {

    MaterialButton btnCurrentLocation;
    public static LocationInterface locationInterface;
    private LocationManager locationManager;
    Location newLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        btnCurrentLocation = view.findViewById(R.id.btn_current_location);

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getActivity(),
                        ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            ACCESS_FINE_LOCATION)) {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{ACCESS_FINE_LOCATION}, 1);
                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{ACCESS_FINE_LOCATION}, 1);

                    }

                }
            }
        });
        return view;
    }

}
