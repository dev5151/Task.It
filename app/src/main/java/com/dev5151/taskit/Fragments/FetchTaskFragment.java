package com.dev5151.taskit.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dev5151.taskit.Activities.DashboardActivity;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FetchTaskFragment extends Fragment {

    DatabaseReference taskRef;
    private List<String> taskIdList;
    private List<Tasks> tasksList;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fetch_task, container, false);
        initView(view);
        fetchTasks();
        return view;
    }

    private void initView(View view) {
        taskRef = FirebaseDatabase.getInstance().getReference().child("tasks");
        taskIdList = new ArrayList<>();
        tasksList = new ArrayList<>();
    }

    private void fetchTasks() {
        taskRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskIdList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Tasks task = dataSnapshot1.getValue(Tasks.class);
                    if (task.getUid() != FirebaseAuth.getInstance().getUid()) {
                        tasksList.add(task);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
