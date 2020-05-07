package com.dev5151.taskit.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dev5151.taskit.Activities.DashboardActivity;
import com.dev5151.taskit.R;

public class AccountFragment extends Fragment {
    TextView name, phone, faq, about, wallet, share, rate, logout;
    ImageButton edit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initView(view);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashboardActivity.getBottomNavBarControlInterface().launchBottomSheetEditDetails();
            }
        });
        return view;
    }

    private void initView(View view) {
        name = view.findViewById(R.id.name);
        phone = view.findViewById(R.id.phone);
        faq = view.findViewById(R.id.faq);
        about = view.findViewById(R.id.about);
        share = view.findViewById(R.id.share);
        wallet = view.findViewById(R.id.wallet);
        rate = view.findViewById(R.id.rate);
        logout = view.findViewById(R.id.logout);
        edit = view.findViewById(R.id.edit);
    }
}
