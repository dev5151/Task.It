package com.dev5151.taskit.Fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
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

public class GivenTasksFragment extends Fragment {

    DatabaseReference userRef;
    DatabaseReference taskRef;
    List<String> taskIdList;
    List<Tasks> tasksList;
    String uid;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_given_tasks, container, false);
        initView(view);
        fetchTasksGivenList();
        return view;
    }

    private void initView(View view) {
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        taskRef = FirebaseDatabase.getInstance().getReference().child("tasks");
        taskIdList = new ArrayList<>();
        tasksList = new ArrayList<>();
        uid = FirebaseAuth.getInstance().getUid();
    }

    private void fetchTasksGivenList() {
        userRef.child("uid").child("taskGiven").addListenerForSingleValueEvent(new ValueEventListener() {
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
                                tasksList.add(0, snap.getValue(Tasks.class));
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
