package com.dev5151.taskit.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev5151.taskit.Adapters.PostedTasksAdapter;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.Tasks;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

      /*  recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        sharedPreferences = getContext().getSharedPreferences("User Details", Context.MODE_PRIVATE);
        phone = sharedPreferences.getString("phone", null);
        taskList = new ArrayList<>();
        keyList = new ArrayList<>();
*/

        //myRef = FirebaseDatabase.getInstance().getReference();

        /*myRef.child("users").child(phone).child("tasks").child("taskGiven").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keyList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getValue().toString();
                    keyList.add(key);

                }

                myRef.child("tasks").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (String key : keyList) {
                            if (dataSnapshot.child(key).exists()) {
                                DataSnapshot snap = dataSnapshot.child(key);
                                Tasks task=new Tasks(snap.child("title").getValue().toString(),
                                        snap.child("description").getValue().toString(),
                                        snap.child("basePrice").getValue().toString(),
                                        snap.child("amount").getValue().toString(),
                                        snap.child("location").getValue().toString(),
                                        snap.child("unixTime").getValue().toString(),
                                        (int)snap.child("flag").getValue(),
                                        snap.child("creatorId").getValue().toString());

                                taskList.add(0,task);
                        }

                    }
                        adapter = new PostedTasksAdapter(getContext(), taskList);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyItemInserted(0);
                        recyclerView.smoothScrollToPosition(0);
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

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();

            }
        });*/
        return view;


    }

}
