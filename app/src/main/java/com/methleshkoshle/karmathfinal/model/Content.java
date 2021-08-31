package com.methleshkoshle.karmathfinal.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity
public class Content {
    @PrimaryKey
    public int id;

    @ColumnInfo
    public String category;

    @ColumnInfo
    public String type;

    @ColumnInfo
    public boolean favorite=false;

    @ColumnInfo
    public String content;

    public static class ContentTextList {
        public List<Content> contentList;
    }
}

