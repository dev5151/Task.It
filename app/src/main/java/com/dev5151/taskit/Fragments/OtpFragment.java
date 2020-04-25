package com.dev5151.taskit.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.dev5151.taskit.Activities.DashboardActivity;
import com.dev5151.taskit.Activities.LocationActivity;
import com.dev5151.taskit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class OtpFragment extends Fragment {

    FirebaseAuth mAuth;
    EditText edtOtp;
    MaterialButton verify;
    String phoneNumber;
    String verificationId;
    ProgressBar progressBar;
    DatabaseReference databaseReference;
    TextView tvPhoneNum;
    String uid;
    private final int interval = 60000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_otp, container, false);

        mAuth = FirebaseAuth.getInstance();
        edtOtp = view.findViewById(R.id.edtOtp);
        verify = view.findViewById(R.id.verify);
        progressBar = view.findViewById(R.id.progress_bar);
        tvPhoneNum = view.findViewById(R.id.phone_number);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        Bundle bundle = getArguments();
        phoneNumber = bundle.getString("phoneNumber");

        tvPhoneNum.setText(phoneNumber);
        sendVerificationCode(phoneNumber);


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = edtOtp.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    edtOtp.setError("Enter the Code ...");
                    edtOtp.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

        return view;
    }

    private void sendVerificationCode(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNumber,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                //Code is Detected Automatically
                edtOtp.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId = s;
            setBtn();
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            uid = mAuth.getUid();
                            databaseReference.child(uid).child("phoneNumber").setValue(phoneNumber);
                            Toast.makeText(getContext(), "LOGIN SUCCESS", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getActivity(), LocationActivity.class));
                            getActivity().finish();
                        } else {
                            Toast.makeText(getContext(), "LOGIN FAILURE", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private void setBtn() {
        progressBar.setVisibility(View.GONE);
        verify.setText("Register");
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
}
