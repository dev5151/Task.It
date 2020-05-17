package com.dev5151.taskit.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev5151.taskit.Activities.DashboardActivity;
import com.dev5151.taskit.Adapters.TaskRequestAdapter;
import com.dev5151.taskit.Interfaces.ItemClickListener;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.TaskRequestModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ApplicantsListFragment extends Fragment {

    private DatabaseReference userRef;
    private String uid;
    private List<TaskRequestModel> taskRequestList = null;
    RecyclerView recyclerView;
    ImageView imgEmpty;
    MaterialToolbar toolbar;
    ItemClickListener itemClickListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applicants_list, container, false);
        initViews(view);
        initToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        fetchTaskRequestList();
        return view;
    }

    private void initViews(View view) {
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        uid = FirebaseAuth.getInstance().getUid();
        toolbar = view.findViewById(R.id.toolbar);
        taskRequestList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        imgEmpty = view.findViewById(R.id.img_empty);
        itemClickListener = DashboardActivity.itemClickListener;
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Applications List");
        toolbar.setNavigationIcon(R.drawable.ic_back);
    }

    private void fetchTaskRequestList() {
        userRef.child(uid).child("taskRequestList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskRequestList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    TaskRequestModel taskRequest = dataSnapshot1.getValue(TaskRequestModel.class);
                    taskRequestList.add(taskRequest);
                }

                if (taskRequestList.size() == 0) {
                    imgEmpty.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setAdapter(new TaskRequestAdapter(taskRequestList, getActivity(), "horizontal_recycler_view", itemClickListener));
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
                    recyclerView.addItemDecoration(dividerItemDecoration);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }
}
