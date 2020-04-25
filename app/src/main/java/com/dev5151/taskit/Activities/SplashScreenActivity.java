package com.dev5151.taskit.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.dev5151.taskit.R;
import com.google.firebase.auth.FirebaseAuth;

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
                    startActivity(new Intent(SplashScreenActivity.this, LocationActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, AuthActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
