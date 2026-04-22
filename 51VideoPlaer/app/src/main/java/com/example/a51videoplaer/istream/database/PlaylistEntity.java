package com.example.a51videoplaer.istream.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "playlist")
public class PlaylistEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int userId; // 🔥 links playlist to user

    public String videoUrl;
}