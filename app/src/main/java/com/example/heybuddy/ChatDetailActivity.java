package com.example.heybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.heybuddy.Adapters.ChatAdapter;
import com.example.heybuddy.Models.Messages;
import com.example.heybuddy.databinding.ActivityChatDetailBinding;
import com.example.heybuddy.fragments.ChatFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class ChatDetailActivity extends AppCompatActivity {

    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        String userName = getIntent().getStringExtra("username");
        String profilepic = getIntent().getStringExtra("profilepic");
        String senderId = auth.getUid();
        String reciverId = getIntent().getStringExtra("userid");

        String sender_Room = senderId + reciverId;
        String reciver_Room = reciverId + senderId;

        Picasso.get().load(profilepic).placeholder(R.drawable.facebook).into(binding.profileImage);
        binding.usernameChatdetail.setText(userName);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<Messages> messages = new ArrayList<>();

        ChatAdapter adapter = new ChatAdapter(messages,this);
        binding.recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);

        binding.sentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.messageArea.getText().toString();
                Messages messageModel = new Messages(senderId,message);
                messageModel.setTimestamp(new Date().getTime());
                binding.messageArea.setText("");


                database.getReference().child("chats").child(sender_Room)
                        .push().setValue(messageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("chats").child(reciver_Room)
                                        .push().setValue(messageModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                            }
                        });

            }
        });

        database.getReference().child("chats").child(sender_Room).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Messages message = dataSnapshot.getValue(Messages.class);
                    messages.add(message);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}