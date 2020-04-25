package com.dev5151.taskit.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import com.dev5151.taskit.Fragments.LoginFragment;
import com.dev5151.taskit.Fragments.OtpFragment;
import com.dev5151.taskit.Interfaces.FlowControlInterface;
import com.dev5151.taskit.R;

public class AuthActivity extends AppCompatActivity {

    private static FlowControlInterface controlInterface;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        fragmentManager = getSupportFragmentManager();

        launchFragment(new LoginFragment());

        controlInterface = new FlowControlInterface() {
            @Override
            public void launchOtp(String phoneNumber) {
                Bundle bundle = new Bundle();
                bundle.putString("phoneNumber", phoneNumber);
                OtpFragment otpFragment = new OtpFragment();
                otpFragment.setArguments(bundle);
                launchFragment(otpFragment);
            }

            @Override
            public void launchLogin() {

                launchFragment(new LoginFragment());

            }
        };
    }

    private void launchFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, "").commit();
    }

    public static FlowControlInterface getControlInterface() {
        return controlInterface;
    }
}
