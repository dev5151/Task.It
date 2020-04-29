package com.dev5151.taskit.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.dev5151.taskit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreenActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_activtiy);

        mAuth = FirebaseAuth.getInstance();
        int SPLASH_TIME_OUT = 700;
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAuth.getCurrentUser() != null) {
                    fetchUser();
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, AuthActivity.class));
                    finish();
                }

                fetchUser();
            }
        }, SPLASH_TIME_OUT);
    }

    private void fetchUser() {
        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getUid()).child("location").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String location = dataSnapshot.getValue(String.class);
                if (location != null) {
                    startActivity(new Intent(SplashScreenActivity.this, DashboardActivity.class));
                    finish();
                } else {
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
