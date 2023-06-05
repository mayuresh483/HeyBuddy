package com.example.heybuddy.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.heybuddy.Dao.NoteDao;
import com.example.heybuddy.Models.Note;
import com.example.heybuddy.Respo.NoteRepo;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepo noteRepo;
    private LiveData<List<Note>> noteList;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepo = new NoteRepo(application);
        noteList= noteRepo.getAllNotes();
    }

    public void insert(Note note){
        noteRepo.insertData(note);
    }
    public void delete(Note note){
        noteRepo.deleteData(note);
    }
    public void update(Note note){
        noteRepo.updateData(note);
    }

    public LiveData<List<Note>> getNoteList(){
        return noteList;
    }
}
