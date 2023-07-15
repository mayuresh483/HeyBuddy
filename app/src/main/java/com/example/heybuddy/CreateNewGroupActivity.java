package com.example.heybuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.heybuddy.Adapters.CreateGroupAdapter;
import com.example.heybuddy.Adapters.UserAdapter;
import com.example.heybuddy.Models.Users;
import com.example.heybuddy.databinding.ActivityCreateNewGroupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class CreateNewGroupActivity extends AppCompatActivity {

    ActivityCreateNewGroupBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    CreateGroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNewGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        ArrayList<Users> model = new ArrayList<>();
        adapter = new CreateGroupAdapter(model,CreateNewGroupActivity.this);
        binding.recyclerView.setAdapter(adapter);

        ArrayList<Users> selectedData = adapter.getSelectedUserData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNewGroupActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                model.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Users users = dataSnapshot.getValue(Users.class);
                    users.getUserId(dataSnapshot.getKey());
                    model.add(users);
                }
                adapter.notifyDataSetChanged();
//                int selectedPositionCount = adapter.getSelectedMembers();
//                int totalMember = adapter.getTotalMembers();
//                String participantText = selectedPositionCount + " of " + (totalMember-1);
//                binding.participantSelectedCount.setText(participantText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.groupnameField.getText().toString().isEmpty()){
                        Toast.makeText(CreateNewGroupActivity.this, "Enter Group Name", Toast.LENGTH_SHORT).show();
                    } else if (selectedData.size() == 0){
                        Toast.makeText(CreateNewGroupActivity.this, "Need Atleast 1 member required to create a group chat", Toast.LENGTH_SHORT).show();
                    } else{
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        FirebaseAuth auth = FirebaseAuth.getInstance();

                        database.getReference().child("groupchats").
                                child(auth.getUid()+binding.groupnameField.getText().toString().replace(" ","")).
                                child("name").setValue(binding.groupnameField.getText().toString());
                        database.getReference().child("groupchats").
                                child(auth.getUid()+binding.groupnameField.getText().toString().replace(" ",""))
                                .child("members").setValue(selectedData);
                        database.getReference().child("groupchats").
                            child(auth.getUid()+binding.groupnameField.getText().toString().replace(" ",""))
                            .child("timestamp").setValue(new Date().getTime());
                        database.getReference().child("groupchats").
                            child(auth.getUid()+binding.groupnameField.getText().toString().replace(" ",""))
                            .child("groupId").setValue(auth.getUid()+binding.groupnameField.getText().toString().replace(" ",""));
                    Toast.makeText(CreateNewGroupActivity.this, "New Group "+binding.groupnameField.getText().toString()+" Successfully created", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CreateNewGroupActivity.this, GroupChatDetailActivity.class);
                    intent.putExtra("groupid",auth.getUid()+binding.groupnameField.getText().toString().replace(" ",""));
                    intent.putExtra("groupname",binding.groupnameField.getText().toString());
                    startActivity(intent);
                }
            }
        });

    }
}