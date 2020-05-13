package com.dev5151.taskit.Fragments;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.dev5151.taskit.R;
import com.dev5151.taskit.models.Tasks;
import com.dev5151.taskit.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class PostTaskFragment extends Fragment {

    private EditText edtTitle, edtDesc, edtAmt, edtExtra;
    private TextView tvAttachments, tvDate, tvTime;
    private ImageView imgAttachments;
    private Button post;
    private DatabaseReference myRef;
    private String unixTime;
    private MaterialToolbar toolbar;
    StorageReference storageReference;
    String imageUrl;
    private Uri filePath;
    Bitmap bitmap;
    StorageTask uploadTask;
    private DatabaseReference userRef;
    private List<String> taskList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_task, container, false);

        intiViews(view);
        initToolbar();

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DatePickerTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                tvDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), R.style.TimePickerTheme,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                               /* String AM_PM;
                                if (hourOfDay < 12) {
                                    AM_PM = "AM";
                                } else {
                                    AM_PM = "PM";
                                }
                                tvTime.setText(hourOfDay + ":" + minute + " " + AM_PM);*/
                                boolean isPM = (hourOfDay >= 12);
                                tvTime.setText(String.format("%02d:%02d %s", (hourOfDay == 12 || hourOfDay == 0) ? 12 : hourOfDay % 12, minute, isPM ? "PM" : "AM"));
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        tvAttachments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
                imageUrl = String.valueOf(filePath);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edtTitle.getText().toString();
                String description = edtDesc.getText().toString();
                String amount = edtAmt.getText().toString();
                String extra = edtExtra.getText().toString();
                String time = tvTime.getText().toString();
                String date = tvDate.getText().toString();
                unixTime = String.valueOf(System.currentTimeMillis() / 1000L);
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String currentDate = df.format(c);
                String currentTime = new SimpleDateFormat("HH:mm:ss aa", Locale.getDefault()).format(new Date());
                myRef.child(unixTime).setValue(new Tasks(title, description, amount, extra, 1, FirebaseAuth.getInstance().getUid(), date, time, currentTime, currentDate, imageUrl, unixTime)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        edtTitle.setText(null);
                        edtDesc.setText(null);
                        edtAmt.setText(null);
                        edtExtra.setText(null);
                        tvTime.setText(null);
                        tvDate.setText(null);
                        imgAttachments.setVisibility(View.GONE);
                    }
                });

                addToTaskGivenList(unixTime);
            }
        });
        return view;
    }

    private void intiViews(View view) {
        edtTitle = view.findViewById(R.id.edt_title);
        edtDesc = view.findViewById(R.id.edt_description);
        edtAmt = view.findViewById(R.id.edt_amt);
        edtExtra = view.findViewById(R.id.edt_extra);
        tvDate = view.findViewById(R.id.tv_date);
        tvTime = view.findViewById(R.id.tv_time);
        tvAttachments = view.findViewById(R.id.tv_attachments);
        toolbar = view.findViewById(R.id.toolbar);
        imgAttachments = view.findViewById(R.id.img_attachment);
        post = view.findViewById(R.id.post);
        myRef = FirebaseDatabase.getInstance().getReference().child("tasks");
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Post your task");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data.getData() != null) {

            filePath = data.getData();
            try {
                imgAttachments.setVisibility(View.VISIBLE);
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                imgAttachments.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
        uploadImage();
    }

    private void uploadImage() {
        if (filePath != null) {

            final StorageReference reference = storageReference.child("images/" + UUID.randomUUID().toString());
            uploadTask = reference.putFile(filePath);

            reference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            filePath = uri;
                            imgAttachments.setVisibility(View.VISIBLE);
                            imgAttachments.setImageBitmap(bitmap);
                        }
                    });
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    private void addToTaskGivenList(final String taskId) {
        userRef.child(FirebaseAuth.getInstance().getUid()).child("taskGiven").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String postedTask = dataSnapshot1.getValue(String.class);
                    taskList.add(0, postedTask);
                }
                taskList.add(taskId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}


