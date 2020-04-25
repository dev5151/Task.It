package com.dev5151.taskit.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

import com.dev5151.taskit.Activities.AuthActivity;
import com.dev5151.taskit.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    private FirebaseAuth mAuth;
    private String phoneNumber;
    private EditText edtPhone;
    private MaterialButton generateOtp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();
        edtPhone = view.findViewById(R.id.et_phone);
        generateOtp = view.findViewById(R.id.btn_sign_in);


        generateOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtPhone.getText().toString().equals(null) || edtPhone.getText().equals("")) {
                    Toast.makeText(getActivity(), "Phone Number can't be null", Toast.LENGTH_LONG).show();
                } else if (edtPhone.getText().toString().length() < 10) {
                    Toast.makeText(getActivity(), "Numbers cannot be less than 10 digits", Toast.LENGTH_LONG).show();
                } else {
                    phoneNumber = edtPhone.getText().toString();
                    AuthActivity.getControlInterface().launchOtp(phoneNumber);
                }

            }
        });

        return view;

    }




}
