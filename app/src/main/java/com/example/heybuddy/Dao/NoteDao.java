package com.example.heybuddy.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.heybuddy.Models.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    public void Insert(Note note);

    @Update
    public void Update(Note note);

    @Delete
    public void Delete(Note note);

    @Query("Select * From My_Notes")
    public LiveData<List<Note>> getAllNotes();
}
