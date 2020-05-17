package com.dev5151.taskit.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dev5151.taskit.Activities.ApplicantsActivity;
import com.dev5151.taskit.Activities.MessageActivity;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.Tasks;
import com.dev5151.taskit.models.User;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ApplicantsDetailsFragment extends Fragment {
    private String applicantUid;
    private String taskId;
    private TextView tvTitle, tvTime, tvDescription, tvName, tvPhoneNumber, tvPrice, tvBudget, tvAttachments;
    private ImageView imgAttachments;
    private CardView cardView;
    private RatingBar ratingBar;
    private MaterialToolbar toolbar;
    private Button btnChat, btnTask;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef, taskRef;
    private ConstraintLayout constraintLayout;
    private List<String> taskFetchedList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applicants_details, container, false);
        initViews(view);
        initToolbar();
        fetchTask();
        fetchApplicant();

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MessageActivity.class);
                intent.putExtra("uid", applicantUid);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assignTask(taskId);
            }
        });

        return view;
    }

    private void initViews(View view) {
        taskId = getArguments().getString("taskId");
        applicantUid = getArguments().getString("applicantUid");
        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        taskRef = FirebaseDatabase.getInstance().getReference().child("tasks").child(taskId);
        toolbar = view.findViewById(R.id.toolbar);
        tvTitle = view.findViewById(R.id.tv_title);
        tvTime = view.findViewById(R.id.tv_time);
        tvDescription = view.findViewById(R.id.tv_description);
        tvPrice = view.findViewById(R.id.tv_amt);
        tvBudget = view.findViewById(R.id.tv_budget);
        cardView = view.findViewById(R.id.cardView);
        tvName = view.findViewById(R.id.tv_name);
        tvPhoneNumber = view.findViewById(R.id.tv_mob_no);
        ratingBar = view.findViewById(R.id.rating_bar);
        btnChat = view.findViewById(R.id.chat);
        btnTask = view.findViewById(R.id.post);
        tvAttachments = view.findViewById(R.id.tv_attachments);
        imgAttachments = view.findViewById(R.id.img_attachment);
        constraintLayout = view.findViewById(R.id.constraint_layout);
        taskFetchedList = new ArrayList<>();
    }

    private void initToolbar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("Assign Task");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicantsActivity.getApplicantsInterface().launchApplicantsList();
            }
        });
    }

    private void fetchTask() {
        taskRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot snap = dataSnapshot;
                if (snap.exists()) {
                    Tasks task = snap.getValue(Tasks.class);
                    String title = task.getTitle();
                    String desc = task.getDesc();
                    String amt = task.getItem_price();
                    String extra = task.getService_amt();
                    String attachment = task.getImgUrl();
                    String endDate = task.getTill_date();
                    int state = task.getState();
                    String currentDate = getCurrentDate();
                    //int daysLeft = getCountOfDays(currentDate, endDate);
                    int daysLeft = 0;

                    if (state == 0) {
                        btnTask.setEnabled(false);
                        showErrorSnackBar("Oops! Seems like task is already taken by someone");
                    }
                    setTask(title, daysLeft, desc, amt, extra, attachment);
                } else {
                    showErrorSnackBar("Error while fetching task");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showErrorSnackBar(databaseError.getMessage());
            }
        });
    }

    private void fetchApplicant() {
        userRef.child(applicantUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot snapshot = dataSnapshot;
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    String name = user.getName();
                    String mobNo = user.getPhone();
                    Float rating = (float) user.getRating();
                    String registrationToken = user.getToken();
                    setCard(name, rating, mobNo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showErrorSnackBar(databaseError.getMessage());
            }
        });
    }

    private void setTask(String title, Integer openClose, String description, String amount, String extra, String attachment) {
        tvTitle.setText(title);
        tvDescription.setText(description);
        tvPrice.setText(amount);
        tvBudget.setText(extra);
        tvTime.setText(String.valueOf(openClose));
        if (attachment != null) {
            tvAttachments.setVisibility(View.VISIBLE);
            imgAttachments.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(attachment).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).into(imgAttachments);
        }
    }

    private void setCard(String employerName, Float rating, String mobNo) {
        cardView.setVisibility(View.VISIBLE);
        tvName.setText(employerName);
        ratingBar.setRating(rating);
        tvPhoneNumber.setText(mobNo);
    }

    private void showErrorSnackBar(String error) {
        Snackbar snackbar = Snackbar.make(constraintLayout, error, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.RED);
        snackbar.setTextColor(Color.BLACK);
        snackbar.show();
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = df.format(c);
        return (currentDate);
    }

    /*public int getCountOfDays(String createdDateString, String expireDateString) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

    Date createdConvertedDate = null, expireConvertedDate = null, todayWithZeroTime = null;
        try

    {
        createdConvertedDate = dateFormat.parse(createdDateString);
        expireConvertedDate = dateFormat.parse(expireDateString);

        Date today = new Date();

        todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
    } catch(
    ParseException e)

    {
        e.printStackTrace();
    }

    int cYear = 0, cMonth = 0, cDay = 0;

        if(createdConvertedDate.after(todayWithZeroTime))

    {
        Calendar cCal = Calendar.getInstance();
        cCal.setTime(createdConvertedDate);
        cYear = cCal.get(Calendar.YEAR);
        cMonth = cCal.get(Calendar.MONTH);
        cDay = cCal.get(Calendar.DAY_OF_MONTH);

    } else

    {
        Calendar cCal = Calendar.getInstance();
        cCal.setTime(todayWithZeroTime);
        cYear = cCal.get(Calendar.YEAR);
        cMonth = cCal.get(Calendar.MONTH);
        cDay = cCal.get(Calendar.DAY_OF_MONTH);
    }

    Calendar eCal = Calendar.getInstance();
        eCal.setTime(expireConvertedDate);

    int eYear = eCal.get(Calendar.YEAR);
    int eMonth = eCal.get(Calendar.MONTH);
    int eDay = eCal.get(Calendar.DAY_OF_MONTH);

    Calendar date1 = Calendar.getInstance();
    Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(cYear,cMonth,cDay);
        date2.clear();
        date2.set(eYear,eMonth,eDay);

    long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

    float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return((int)dayCount);
}
*/
    private void assignTask(final String taskId) {
        taskRef.child("state").setValue(0);
        userRef.child(applicantUid).child("taskFetched").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                taskFetchedList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    String task = dataSnapshot1.getValue(String.class);
                    taskFetchedList.add(task);
                }
                taskFetchedList.add(taskId);
                userRef.child(applicantUid).child("taskFetched").setValue(taskFetchedList);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
