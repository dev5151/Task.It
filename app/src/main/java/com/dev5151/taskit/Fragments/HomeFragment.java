package com.dev5151.taskit.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev5151.taskit.Adapters.PostedTasksAdapter;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.Tasks;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private DatabaseReference myRef;
    private SharedPreferences sharedPreferences;
    private String phone;
    private ArrayList<Tasks> taskList;
    private ArrayList<String> keyList;
    PostedTasksAdapter adapter;

    private AppBarLayout appBarLayout;
    private MaterialToolbar toolbar;
    private TextView tvLocation;
    DatabaseReference userRef;
    String uid;
    FirebaseAuth mAuth;
    String location;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initToolbar();
        return view;

    }

    private void initView(View view) {
        appBarLayout = view.findViewById(R.id.app_bar_layout);
        toolbar = view.findViewById(R.id.toolbar);
        tvLocation = view.findViewById(R.id.tv_location);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("location");

    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                location = dataSnapshot.getValue(String.class);
                tvLocation.setText(location);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
