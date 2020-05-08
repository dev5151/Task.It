package com.dev5151.taskit.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.dev5151.taskit.Fragments.AccountFragment;
import com.dev5151.taskit.Fragments.BottomSheetEditDetails;
import com.dev5151.taskit.Fragments.ChatFragment;
import com.dev5151.taskit.Fragments.FetchTaskFragment;
import com.dev5151.taskit.Fragments.HomeFragment;
import com.dev5151.taskit.Fragments.PostTaskFragment;
import com.dev5151.taskit.Interfaces.BottomNavBarControlInterface;
import com.dev5151.taskit.Interfaces.FlowControlInterface;
import com.dev5151.taskit.Interfaces.ItemClickListener;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.Tasks;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenu;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class DashboardActivity extends AppCompatActivity {

    com.google.android.material.bottomnavigation.BottomNavigationView bottomNavigationView;

    private static BottomNavBarControlInterface flowControlInterface;
    FragmentManager fragmentManager;
    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    public static ItemClickListener itemClickListener;
    private static final String TAG = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getInstanceId();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();

        flowControlInterface = new BottomNavBarControlInterface() {
            @Override
            public void launchHome() {
                launchFragment(new HomeFragment());
            }

            @Override
            public void launchAccount() {
                launchFragment(new AccountFragment());
            }

            @Override
            public void launchPostTask() {
                launchFragment(new PostTaskFragment());
            }

            @Override
            public void launchFetchTask() {
                launchFragment(new FetchTaskFragment());
            }

            @Override
            public void launchMessages() {
                launchFragment(new ChatFragment());
            }

            @Override
            public void goToTask(int i, String taskId) {
                Intent intent = new Intent(DashboardActivity.this, TaskActivity.class);
                intent.putExtra("taskId", taskId);
                intent.putExtra("flag", i);
                startActivity(intent);
            }

            @Override
            public void launchBottomSheetEditDetails() {
                BottomSheetEditDetails bottomSheetEditDetailsFragment = new BottomSheetEditDetails(this);
                bottomSheetEditDetailsFragment.show(getSupportFragmentManager(), bottomSheetEditDetailsFragment.getTag());
            }
        };

        itemClickListener = new ItemClickListener() {
            @Override
            public void onClickTask(Tasks task) {
                getBottomNavBarControlInterface().goToTask(task.getState(), task.getUnix());
            }
        };

        getBottomNavBarControlInterface().launchHome();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    getBottomNavBarControlInterface().launchHome();
                    return true;
                case R.id.post:
                    getBottomNavBarControlInterface().launchPostTask();
                    return true;
                case R.id.search:
                    getBottomNavBarControlInterface().launchFetchTask();
                    return true;
                case R.id.account:
                    getBottomNavBarControlInterface().launchAccount();
                    return true;
                case R.id.messages:
                    getBottomNavBarControlInterface().launchMessages();
                    return true;
            }

            return false;
        }
    };

    private void launchFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment, "").commit();
    }

    public static BottomNavBarControlInterface getBottomNavBarControlInterface() {
        return flowControlInterface;
    }

    private void getInstanceId() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("token").setValue(token);

                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }


}

