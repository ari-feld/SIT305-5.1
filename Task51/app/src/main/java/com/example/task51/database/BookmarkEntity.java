package com.example.task51.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmarks")
public class BookmarkEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String description;
    public int imageRes;
    public String category;

    public BookmarkEntity(String title, String description, int imageRes, String category) {
        this.title = title;
        this.description = description;
        this.imageRes = imageRes;
        this.category = category;
    }
}