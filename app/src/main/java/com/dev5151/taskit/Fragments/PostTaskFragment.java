package com.dev5151.taskit.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.dev5151.taskit.R;
import com.dev5151.taskit.models.Tasks;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Instant;

public class PostTaskFragment extends Fragment {

    private EditText edt1, edt2, edt3, edt4, edt5;
    private Button post;
    private DatabaseReference myRef;
    private String phone;
    private String unixTime;
    private SharedPreferences sharedPreferences;
    private int flag = 1;
    private String creatorId;
    private MaterialToolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_task, container, false);

        edt1 = view.findViewById(R.id.edt1);
        edt2 = view.findViewById(R.id.edt2);
        edt3 = view.findViewById(R.id.edt3);
        edt4 = view.findViewById(R.id.edt4);
        edt5 = view.findViewById(R.id.edt5);
        post = view.findViewById(R.id.post);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Post your task");
        sharedPreferences = getContext().getSharedPreferences("User Details", Context.MODE_PRIVATE);
        phone = sharedPreferences.getString("phone", null);

        creatorId = phone;
        myRef = FirebaseDatabase.getInstance().getReference();

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edt1.getText().toString();
                String description = edt2.getText().toString();
                String amount = edt3.getText().toString();
                String basePrice = edt4.getText().toString();
                String location = edt5.getText().toString();
                String unixTime = String.valueOf(System.currentTimeMillis() / 1000L);

                String key = myRef.child("tasks").push().getKey();
                myRef.child("tasks").child(unixTime).setValue(new Tasks(title, description, amount, basePrice, location, unixTime, flag, creatorId));
                Log.d("KEY", key);
                post.setClickable(false);
                myRef.child("users").child(phone).child("tasks").child("taskGiven").push().setValue(unixTime);


            }
        });
        return view;
    }
}


