package com.dev5151.taskit.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class CompletedTasksFragment extends Fragment {

    DatabaseReference userRef;
    DatabaseReference taskRef;
    String uid;
    List<String> taskIdList;
    List<Tasks> tasksList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed_tasks, container, false);
        return view;

    }

    private void initViews() {
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        taskRef = FirebaseDatabase.getInstance().getReference().child("tasks");
        uid = FirebaseAuth.getInstance().getUid();
        taskIdList = new ArrayList<>();
        tasksList = new ArrayList<>();
    }

    private void fetchCompletedTasks() {
        userRef.child("uid").child("taskFetched").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskIdList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String taskId = dataSnapshot1.getValue(String.class);
                    taskIdList.add(0, taskId);
                }
                taskRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (String taskId : taskIdList) {
                            if (dataSnapshot.child(taskId).exists()) {
                                DataSnapshot snap = dataSnapshot.child(taskId);
                                Tasks task = snap.getValue(Tasks.class);
                                if (task.getState() == 0) {
                                    tasksList.add(0, task);
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

