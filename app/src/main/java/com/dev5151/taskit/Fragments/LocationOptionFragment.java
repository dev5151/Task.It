package com.dev5151.taskit.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dev5151.taskit.Activities.LocationActivity;
import com.dev5151.taskit.R;
import com.google.android.material.button.MaterialButton;

public class LocationOptionFragment extends Fragment {

    private MaterialButton btnCurrentLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_option, container, false);
        btnCurrentLocation = view.findViewById(R.id.btn_current_location);

        btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationActivity.getLocationInterface().switchToMaps();
            }
        });
        return view;
    }
}
