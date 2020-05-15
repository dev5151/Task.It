package com.dev5151.taskit.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev5151.taskit.Adapters.TaskRequestAdapter;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.TaskRequestModel;
import com.dev5151.taskit.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TasksRequestFragment extends Fragment {

    private DatabaseReference userRef;
    private String uid;
    private List<TaskRequestModel> taskRequestList = null;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request_tasks, container, false);
        initViews(view);
        fetchTaskRequestList();
        return view;
    }

    private void initViews(View view) {
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        uid = FirebaseAuth.getInstance().getUid();
        taskRequestList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
    }

    private void fetchTaskRequestList() {
        userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskRequestList.clear();
                User user = dataSnapshot.getValue(User.class);
                taskRequestList = user.getTaskRequestList();
                recyclerView.setAdapter(new TaskRequestAdapter(taskRequestList, getActivity(), "horizontal_recycler_view"));
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
                recyclerView.addItemDecoration(dividerItemDecoration);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
