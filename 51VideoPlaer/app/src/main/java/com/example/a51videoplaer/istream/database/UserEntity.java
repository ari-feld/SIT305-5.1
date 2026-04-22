package com.example.a51videoplaer.istream.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String fullName;
    public String username;
    public String password;
}