package com.dev5151.taskit.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dev5151.taskit.Interfaces.FlowControlInterface;
import com.dev5151.taskit.R;

public class MainActivity extends AppCompatActivity {
    private static FlowControlInterface controlInterface;
    FragmentManager fragmentManager;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn_switch);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                finish();
            }
        });

    }
}
