package com.dev5151.taskit.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.Tasks;
import com.dev5151.taskit.models.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TaskActivity extends AppCompatActivity {

    private Toolbar toolbar;
    DatabaseReference taskRef, userRef;
    FirebaseAuth mAuth;
    String employerUid, taskId;
    private ConstraintLayout constraintLayout;
    private TextView tvTitle, tvStatus, tvDescription, tvAmt, tvExtra, tvAttachment, tvName, tvMobNo;
    private ImageView imgAttachment;
    private CardView cardView;
    private RatingBar ratingBar;
    private Button post;
    private Integer state;
    private String currentDate;
    private String endDate;
    private Integer daysLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        initView();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fetchTaskDetails();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();
        taskId = getIntent().getStringExtra("taskId");
        userRef=FirebaseDatabase.getInstance().getReference().child("users");
        taskRef = FirebaseDatabase.getInstance().getReference().child("tasks").child(taskId);
        tvTitle = findViewById(R.id.tv_title);
        tvDescription = findViewById(R.id.tv_description);
        tvAmt = findViewById(R.id.tv_amt);
        tvExtra = findViewById(R.id.tv_extra);
        tvStatus = findViewById(R.id.tv_open_close);
        tvAttachment = findViewById(R.id.tv_attachments);
        imgAttachment = findViewById(R.id.img_attachment);
        cardView = findViewById(R.id.cardView);
        tvName = findViewById(R.id.tv_name);
        tvMobNo = findViewById(R.id.tv_mob_no);
        ratingBar = findViewById(R.id.rating_bar);
        post = findViewById(R.id.post);
        constraintLayout = findViewById(R.id.constraint_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);}

    private void fetchTaskDetails() {
        taskRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 DataSnapshot snap = dataSnapshot;
                if (snap.exists()) {
                    Tasks task = snap.getValue(Tasks.class);
                    String employerUid = task.getUid();
                    String title = task.getTitle();
                    String desc = task.getDesc();
                    String amt = task.getItem_price();
                    String extra = task.getService_amt();
                    String attachment = task.getImgUrl();
                    endDate = task.getTill_date();
                    state = task.getState();

                    currentDate = getCurrentDate();
                   // daysLeft = getCountOfDays(currentDate, endDate);

                    if (state == 0) {
                        post.setEnabled(false);
                        showErrorSnackBar("Oops! Seems like task is already taken by someone");
                    }
                    setTask(title, daysLeft, desc, amt, extra, attachment);

                    userRef.child(employerUid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            DataSnapshot snapshot = dataSnapshot;
                            if (snapshot.exists()) {
                                User user = snapshot.getValue(User.class);
                                String name = user.getName();
                                String mobNo = user.getPhone();
                                Float rating = (float) user.getRating();

                                setCard(name, rating, mobNo);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            showErrorSnackBar(databaseError.getMessage());
                        }
                    });
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

    private void setTask(String title, Integer openClose, String description, String amount, String extra, String attachment) {
        tvTitle.setText(title);
        tvDescription.setText(description);
        tvAmt.setText(amount);
        tvExtra.setText(extra);
        tvStatus.setText(String.valueOf(openClose));
        if (attachment != null) {
            tvAttachment.setVisibility(View.VISIBLE);
            imgAttachment.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(attachment).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).into(imgAttachment);
        }
    }

    private void setCard(String employerName, Float rating, String mobNo) {
        cardView.setVisibility(View.VISIBLE);
        tvName.setText(employerName);
        ratingBar.setRating(rating);
        tvMobNo.setText(mobNo);
    }

    private void showErrorSnackBar(String error) {
        Snackbar snackbar = Snackbar.make(constraintLayout, error, Snackbar.LENGTH_LONG);
        snackbar.setBackgroundTint(Color.RED);
        snackbar.setTextColor(Color.BLACK);
        snackbar.show();
    }

    private void checkState() {
        if (state == 1) {

        } else {
            showErrorSnackBar("Oops! Seems like task is already taken by someone");
            post.setEnabled(false);

        }
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String currentDate = df.format(c);
        return (currentDate);
    }

    public int getCountOfDays(String createdDateString, String expireDateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

        Date createdConvertedDate = null, expireConvertedDate = null, todayWithZeroTime = null;
        try {
            createdConvertedDate = dateFormat.parse(createdDateString);
            expireConvertedDate = dateFormat.parse(expireDateString);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cYear = 0, cMonth = 0, cDay = 0;

        if (createdConvertedDate.after(todayWithZeroTime)) {
            Calendar cCal = Calendar.getInstance();
            cCal.setTime(createdConvertedDate);
            cYear = cCal.get(Calendar.YEAR);
            cMonth = cCal.get(Calendar.MONTH);
            cDay = cCal.get(Calendar.DAY_OF_MONTH);

        } else {
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
        date1.set(cYear, cMonth, cDay);
        date2.clear();
        date2.set(eYear, eMonth, eDay);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        float dayCount = (float) diff / (24 * 60 * 60 * 1000);

        return ((int) dayCount);
    }

}
