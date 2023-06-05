package com.example.heybuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.heybuddy.Adapters.SettingListAdapter;
import com.example.heybuddy.Models.SettingModel;
import com.example.heybuddy.databinding.ActivitySettingBinding;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    ActivitySettingBinding binding;
    ArrayList<SettingModel> settingModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        settingModels = new ArrayList<SettingModel>();

        SettingListAdapter settingListAdapter = new SettingListAdapter(settingModels,this);
        binding.recyclerView.setAdapter(settingListAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);

        binding.profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}