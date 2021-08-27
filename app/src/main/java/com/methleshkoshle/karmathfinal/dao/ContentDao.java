package com.methleshkoshle.karmathfinal.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.methleshkoshle.karmathfinal.model.Content;

import java.util.List;

@Dao
public interface ContentDao {
    @Query("SELECT * FROM content")
    List<Content> getAll();

    @Query("SELECT * FROM content WHERE id=:id")
    Content getById(int id);

    @Query("SELECT * FROM content WHERE category=:category")
    List<Content> loadAllByCategory(String category);

    @Query("SELECT * FROM content WHERE type=:type")
    List<Content> loadAllByType(String type);

    @Query("SELECT * FROM content WHERE category=:category AND type=:type")
    List<Content> loadAllByCategoryAndType(String category, String type);

    @Query("SELECT * FROM content WHERE category=:category AND type=:type AND favorite=:favorite")
    List<Content> loadAllByCategoryAndTypeAndFavorite(String category, String type, boolean favorite);

    @Query("SELECT * FROM content WHERE favorite=:favorite")
    List<Content> loadAllByFavorite(boolean favorite);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Content... contents);

    @Delete
    void delete(Content content);
}
