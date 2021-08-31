package com.methleshkoshle.karmathfinal.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.methleshkoshle.karmathfinal.dao.ContentDao;
import com.methleshkoshle.karmathfinal.model.Content;

@Database(entities = {Content.class}, version = 1, exportSchema = false)
public abstract class ContentDatabase extends RoomDatabase {
    public abstract ContentDao contentDao();
}