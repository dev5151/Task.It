package com.dev5151.taskit.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dev5151.taskit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class SplashScreenActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    String TAG = "instance";
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_activtiy);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress_bar);
        int SPLASH_TIME_OUT = 700;
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAuth.getCurrentUser() != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    fetchUser();
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(SplashScreenActivity.this, AuthActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    private void fetchUser() {
        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid()).child("location").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String location = dataSnapshot.getValue(String.class);
                if (location != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(SplashScreenActivity.this, DashboardActivity.class));
                    finish();
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(SplashScreenActivity.this, LocationActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
