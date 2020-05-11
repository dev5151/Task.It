package com.dev5151.taskit.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev5151.taskit.Activities.LocationActivity;
import com.dev5151.taskit.Adapters.PostedTasksAdapter;
import com.dev5151.taskit.Adapters.TaskRequestAdapter;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.TaskRequestModel;
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
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageView imageView;
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
    List<TaskRequestModel> taskRequestList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initToolbar();

        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LocationActivity.class));
                getActivity().finish();
            }
        });

        fetchRequests();

        return view;

    }

    private void initView(View view) {
        appBarLayout = view.findViewById(R.id.app_bar_layout);
        toolbar = view.findViewById(R.id.toolbar);
        recyclerView = view.findViewById(R.id.recyclerView);
        imageView = view.findViewById(R.id.empty);
        tvLocation = view.findViewById(R.id.tv_location);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
        taskRequestList = new ArrayList<>();
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        userRef.child("location").addListenerForSingleValueEvent(new ValueEventListener() {
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

    private void fetchRequests() {
        userRef.child("tasksRequestList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskRequestList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    TaskRequestModel taskRequestModel = dataSnapshot1.getValue(TaskRequestModel.class);
                    taskRequestList.add(taskRequestModel);
                }
                if (taskRequestList.size() == 0) {
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setAdapter(new TaskRequestAdapter(taskRequestList, getActivity()));
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
