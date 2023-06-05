package com.example.heybuddy.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.heybuddy.Adapters.GroupChatAdapter;
import com.example.heybuddy.CreateNewGroupActivity;
import com.example.heybuddy.Models.GroupChats;
import com.example.heybuddy.databinding.FragmentGroupChatBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupChatFragment extends Fragment {

    public GroupChatFragment() {

    }

    private FragmentGroupChatBinding binding;
    private FirebaseDatabase firebaseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentGroupChatBinding.inflate(inflater, container, false);
        ArrayList<GroupChats> list = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();

        GroupChatAdapter adapter = new GroupChatAdapter(list, getContext());
        binding.groupchatRecyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.groupchatRecyclerView.setLayoutManager(layoutManager);
        firebaseDatabase.getReference().child("groupchats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GroupChats users = dataSnapshot.getValue(GroupChats.class);
                    users.getUserId(dataSnapshot.getKey());
                    list.add(users);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), CreateNewGroupActivity.class);
                startActivity(intent);
            }
        });



        return binding.getRoot();
    }
}