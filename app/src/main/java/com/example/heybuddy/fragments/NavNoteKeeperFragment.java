package com.example.heybuddy.fragments;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.heybuddy.NoteKeepActivity;
import com.example.heybuddy.databinding.FragmentNavNoteKeeperBinding;

public class NavNoteKeeperFragment extends Fragment {

    FragmentNavNoteKeeperBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNavNoteKeeperBinding.inflate(inflater, container, false);
        binding.startAddingNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NoteKeepActivity.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }

}