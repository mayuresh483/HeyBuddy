package com.example.heybuddy.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "My_Notes")
public class Note {
    private String title;
    private String desc;


    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int getId() {
        return id;
    }
    public Note(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}

