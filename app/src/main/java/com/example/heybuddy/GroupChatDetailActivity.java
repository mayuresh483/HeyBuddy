package com.example.heybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.example.heybuddy.Adapters.ChatAdapter;
import com.example.heybuddy.Models.GroupChats;
import com.example.heybuddy.Models.Messages;
import com.example.heybuddy.Models.Users;
import com.example.heybuddy.databinding.ActivityGroupChatDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatDetailActivity extends AppCompatActivity {

    private ActivityGroupChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        String groupName = getIntent().getStringExtra("groupname");
        String profilePic = getIntent().getStringExtra("profilepic");
        String groupId = getIntent().getStringExtra("groupid");

        binding.usernameGroupchatdetail.setText(groupName);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupChatDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<Messages> messages = new ArrayList<>();

        ChatAdapter adapter = new ChatAdapter(messages, this);
        binding.groupchatRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.groupchatRecyclerView.setLayoutManager(layoutManager);

        binding.sentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.messageAreaGroupchat.getText().toString();
                Messages messageModel = new Messages(auth.getUid(), message);
                messageModel.setTimestamp(new Date().getTime());
                messageModel.setGroupId(groupId);
                binding.messageAreaGroupchat.setText("");
                getUserName(messageModel, groupId);


            }
        });

        database.getReference().child("groupchats").child(groupId).child("chats").
                addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messages.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
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

    private void getUserName(Messages model, String groupId) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        database.getReference().child("Users").child(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Users users = task.getResult().getValue(Users.class);

                    model.setUsername(users.getUserName());
                    database.getReference().child("groupchats").child(groupId).child("chats")
                            .push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    model.setLastmessage(model.getMessage());
                                    database.getReference().child("groupchats").child(groupId).child("lastmessage").
                                            setValue(model.getMessage());
                                    database.getReference().child("groupchats").child(groupId).child("timestamp").
                                            setValue(model.getTimestamp());
                                    database.getReference().child("groupchats").child(groupId).child("lastmessageuserid").
                                            setValue(model.getUserId());

                                }
                            });
                }
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.groupchat_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.profile:
//                break;
//
//            case R.id.add_members:
//                break;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
}