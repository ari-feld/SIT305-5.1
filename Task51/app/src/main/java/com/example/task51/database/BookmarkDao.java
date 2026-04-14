package com.example.task51.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookmarkDao {

    @Insert
    void insert(BookmarkEntity item);

    @Query("SELECT * FROM bookmarks")
    List<BookmarkEntity> getAll();

    @Query("DELETE FROM bookmarks WHERE title = :title AND description = :description")
    void deleteByContent(String title, String description);

    @Query("SELECT * FROM bookmarks WHERE title = :title AND description = :description LIMIT 1")
    BookmarkEntity findBookmark(String title, String description);
}