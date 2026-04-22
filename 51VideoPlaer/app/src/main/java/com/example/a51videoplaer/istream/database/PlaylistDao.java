package com.example.a51videoplaer.istream.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlaylistDao {

    @Insert
    void insert(PlaylistEntity item);

    @Query("SELECT * FROM playlist WHERE userId = :userId")
    List<PlaylistEntity> getUserPlaylist(int userId);
}