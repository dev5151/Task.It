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
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.dev5151.taskit.Activities.DashboardActivity;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.User;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {
    TextView tvName, tvPhone, faq, about, wallet, share, rate, logout;
    ImageButton edit;
    MaterialToolbar toolbar;
    DatabaseReference userRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        initView(view);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("My Profile");
        fetchUser();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DashboardActivity.getBottomNavBarControlInterface().launchBottomSheetEditDetails();
            }
        });
        return view;
    }

    private void initView(View view) {
        tvName = view.findViewById(R.id.name);
        tvPhone = view.findViewById(R.id.phone);
        faq = view.findViewById(R.id.faq);
        about = view.findViewById(R.id.about);
        share = view.findViewById(R.id.share);
        wallet = view.findViewById(R.id.wallet);
        rate = view.findViewById(R.id.rate);
        logout = view.findViewById(R.id.logout);
        edit = view.findViewById(R.id.edit);
        toolbar = view.findViewById(R.id.toolbar);
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid());

    }

    private void fetchUser() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String name = user.getName();
                String phoneNum = user.getPhone();
                setValue(name, phoneNum);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setValue(String name, String phoneNum) {
        tvName.setText(name);
        tvPhone.setText(phoneNum);
    }
}
