package com.example.heybuddy.Respo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.heybuddy.Dao.NoteDao;
import com.example.heybuddy.Models.Note;
import com.example.heybuddy.NoteDatabase.NoteDatabase;

import java.util.List;

public class NoteRepo {
    private NoteDao noteDao;
    private NoteDatabase noteDatabase;
    private LiveData<List<Note>> noteList;

    public NoteRepo(Application application){
        noteDatabase = NoteDatabase.getInstance(application.getApplicationContext());
        noteDao = noteDatabase.noteDao();
        noteList = noteDao.getAllNotes();
    }

    public void insertData(Note note){
        new insertTask(noteDao).execute(note);
    }
    public void updateData(Note note){
        new updateTask(noteDao).execute(note);
    }
    public void deleteData(Note note){
        new deleteTask(noteDao).execute(note);
    }

    public LiveData<List<Note>> getAllNotes(){
        return noteList;
    }

    public static class insertTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        public insertTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.Insert(notes[0]);
            return null;
        }
    }

    public static class updateTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        public updateTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.Update(notes[0]);
            return null;
        }
    }

    public static class deleteTask extends AsyncTask<Note,Void,Void>{
        private NoteDao noteDao;

        public deleteTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.Delete(notes[0]);
            return null;
        }
    }
}
