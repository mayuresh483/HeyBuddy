package com.example.heybuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.heybuddy.databinding.ActivityCreateNoteBinding;

public class CreateNoteActivity extends AppCompatActivity {

    ActivityCreateNoteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String type= getIntent().getStringExtra("type");
        String getDesc= getIntent().getStringExtra("desc");
        String getTitle= getIntent().getStringExtra("title");

        if(type.equals("update")){
            setTitle("Update Note");
            binding.titleNote.setText(getTitle);
            binding.descNote.setText(getDesc);
            int id = getIntent().getIntExtra("id",0);
            binding.submitNote.setText("Update Note");
            binding.submitNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("title",binding.titleNote.getText().toString());
                    intent.putExtra("desc",binding.descNote.getText().toString());
                    intent.putExtra("id",id);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        } else{
            setTitle("Add Note");
            binding.submitNote.setText("Add Note");
            binding.submitNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("title",binding.titleNote.getText().toString());
                    intent.putExtra("desc",binding.descNote.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,NoteKeepActivity.class);
        startActivity(intent);
    }
}