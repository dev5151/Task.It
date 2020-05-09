package com.dev5151.taskit.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.dev5151.taskit.Adapters.MessageAdapter;
import com.dev5151.taskit.R;
import com.dev5151.taskit.models.Chat;
import com.dev5151.taskit.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    private ImageView profilePic;
    private TextView username;
    Intent intent;
    androidx.appcompat.widget.Toolbar toolbar;
    String uid;
    DatabaseReference userRef, chatRef;
    private EditText edtMessage;
    private ImageButton send;
    private RecyclerView recyclerView;
    List<Chat> chatList;
    String message;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        initViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        intent = getIntent();
        uid = intent.getStringExtra("uid");

        userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getName());
                ColorGenerator generator = ColorGenerator.MATERIAL;
                int color1 = generator.getRandomColor();
                TextDrawable drawable = TextDrawable.builder()
                        .beginConfig()
                        .textColor(Color.WHITE)
                        .useFont(Typeface.DEFAULT)
                        .fontSize(30) /* size in px */
                        .bold()
                        .toUpperCase()
                        .endConfig()
                        .buildRect(user.getName().substring(0, 1), color1);

                profilePic.setImageDrawable(drawable);

                readMessage(FirebaseAuth.getInstance().getUid(), uid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                message = edtMessage.getText().toString();
                if (!message.equals("")) {
                    sendMessage(FirebaseAuth.getInstance().getUid(), uid, message);
                    edtMessage.setText(null);
                } else {
                    Toast.makeText(getApplicationContext(), "You can't send an empty message", Toast.LENGTH_LONG).show();
                }
            }
        });

        recyclerView.hasFixedSize();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


    }

    private void initViews() {
        username = findViewById(R.id.username);
        toolbar = findViewById(R.id.toolbar);
        profilePic = findViewById(R.id.profilePic);
        userRef = FirebaseDatabase.getInstance().getReference().child("users");
        recyclerView = findViewById(R.id.recyclerView);
        edtMessage = findViewById(R.id.message);
        send = findViewById(R.id.send);
        chatRef = FirebaseDatabase.getInstance().getReference().child("chats");
        chatList = new ArrayList<>();


    }

    private void sendMessage(String sender, String receiver, String message) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        chatRef.push().setValue(hashMap);

        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("chatList")
                .child(FirebaseAuth.getInstance().getUid())
                .child(uid);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    myRef.child("id").setValue(uid);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readMessage(final String myId, final String userId) {

        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Chat chat = dataSnapshot1.getValue(Chat.class);
                    if (chat.getReceiver().equals(myId) && chat.getSender().equals(uid) || chat.getReceiver().equals(userId) && chat.getSender().equals(myId)) {
                        chatList.add(chat);
                    }
                }
                messageAdapter = new MessageAdapter(chatList, MessageActivity.this);
                recyclerView.setAdapter(messageAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
