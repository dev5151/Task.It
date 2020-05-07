package com.dev5151.taskit.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;

import com.dev5151.taskit.Activities.AuthActivity;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.User;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BottomSheetEditDetails extends BottomSheetDialogFragment {

    TextView tvPhoneNum;
    EditText edtName, edtEmail;
    MaterialButton btnSubmit;
    DatabaseReference userRef;
    ProgressBar progressBarHorizontal;
    LinearLayout linearLayoutDetails;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_edit_details, container, false);
        initViews(view);
        fetchUser();

        tvPhoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AuthActivity.class);
                FirebaseAuth.getInstance().signOut();
                startActivity(intent);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
        return view;
    }


    private void initViews(View view) {
        tvPhoneNum = view.findViewById(R.id.tvPhoneNum);
        edtName = view.findViewById(R.id.edtName);
        edtEmail = view.findViewById(R.id.edtEmail);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid());
        progressBarHorizontal = view.findViewById(R.id.progress_bar_horizontal);
        linearLayoutDetails = view.findViewById(R.id.linearLayout2);
        DrawableCompat.setTint(progressBarHorizontal.getIndeterminateDrawable(), Color.BLACK);
    }

    private void fetchUser() {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                String name = user.getName();
                String phoneNum = user.getPhone();
                String email = user.getEmail();
                progressBarHorizontal.setVisibility(View.INVISIBLE);
                setViews(name, phoneNum, email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setViews(String name, String phoneNum, String email) {
        linearLayoutDetails.setVisibility(View.VISIBLE);
        edtName.setText(name);
        tvPhoneNum.setText("+91" + phoneNum);
        edtEmail.setText(email);
    }

    private void submit() {
        if (edtName.getText().toString() == null || edtEmail.getText().toString().equals(null)) {
            showErrorSnackBar("Fields can't be empty !");
        } else {
            String name = edtName.getText().toString();
            String email = edtEmail.getText().toString();
            userRef.child("name").setValue(name);
            userRef.child("email").setValue(email);
        }
    }

    private void showErrorSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(getView(), message, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.RED);
        snackbar.show();
    }

}
