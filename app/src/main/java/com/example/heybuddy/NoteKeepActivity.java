package com.example.heybuddy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.heybuddy.Adapters.NoteAdapter;
import com.example.heybuddy.Models.Note;
import com.example.heybuddy.ViewModel.NoteViewModel;
import com.example.heybuddy.databinding.ActivityNoteKeepBinding;

import java.util.List;

public class NoteKeepActivity extends AppCompatActivity {

    ActivityNoteKeepBinding binding;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteKeepBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle("NoteKeeper");

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteKeepActivity.this, CreateNoteActivity.class);
                intent.putExtra("type","addMode");
                startActivityForResult(intent,1);
            }
        });

        NoteAdapter adapter = new NoteAdapter();
        binding.noteRecyclerview.setAdapter(adapter);
        binding.cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.dialoguebox.setVisibility(View.GONE);
            }
        });

        binding.noteRecyclerview.setLayoutManager(new LinearLayoutManager(NoteKeepActivity.this));
        binding.noteRecyclerview.setHasFixedSize(true);

        noteViewModel = new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(NoteViewModel.class);
        noteViewModel.getNoteList().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.submitList(notes);
            }
        });

        ItemTouchHelper mIth = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        if(direction == ItemTouchHelper.RIGHT){
                            Intent intent = new Intent(NoteKeepActivity.this, CreateNoteActivity.class);
                            intent.putExtra("title",adapter.getNote(viewHolder.getAdapterPosition()).getTitle());
                            intent.putExtra("desc",adapter.getNote(viewHolder.getAdapterPosition()).getDesc());
                            intent.putExtra("id",adapter.getNote(viewHolder.getAdapterPosition()).getId());
                            intent.putExtra("type","update");
                            startActivityForResult(intent,2);
                        } else{
                            noteViewModel.delete(adapter.getNote(viewHolder.getAdapterPosition()));
                            Toast.makeText(NoteKeepActivity.this, "Note Deleted Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        mIth.attachToRecyclerView(binding.noteRecyclerview);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            String title = data.getStringExtra("title");
            String desc = data.getStringExtra("desc");
            Note note = new Note(title,desc);
            noteViewModel.insert(note);
            Toast.makeText(NoteKeepActivity.this, "Note Added Successfully", Toast.LENGTH_SHORT).show();

        }
        if(requestCode == 2){
            String title = data.getStringExtra("title");
            String desc = data.getStringExtra("desc");
            Note note = new Note(title,desc);
            note.setId(data.getIntExtra("id",0));
            noteViewModel.update(note);
            Toast.makeText(NoteKeepActivity.this, "Note Updated Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(NoteKeepActivity.this,MainActivity.class);
        startActivity(intent);
    }
}