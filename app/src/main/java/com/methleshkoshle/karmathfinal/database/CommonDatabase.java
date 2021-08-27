package com.methleshkoshle.karmathfinal.database;

import com.methleshkoshle.karmathfinal.dao.ContentDao;
import com.methleshkoshle.karmathfinal.model.Content;
import com.methleshkoshle.karmathfinal.model.Content.ContentTextList;
import com.methleshkoshle.karmathfinal.pages.ContentActivity;
import com.methleshkoshle.karmathfinal.pages.FavoriteActivity;

import java.util.List;

public class CommonDatabase {
    public static ContentDatabase db;

    public static void getContent(String category, String type){
        ContentDao contentDao = db.contentDao();
        List<Content> contentList = contentDao.loadAllByCategoryAndType(category, type);
        ContentTextList contentTextList = new ContentTextList();
        contentTextList.contentList = contentList;
        ContentActivity.contentViewModel.getCurrentContent().setValue(contentTextList);
    }
    public static void getFavorites(String type){
        ContentDao contentDao = db.contentDao();
        List<Content> contentList = contentDao.loadAllByFavorite(true);
        ContentTextList contentTextList = new ContentTextList();
        contentTextList.contentList = contentList;
        FavoriteActivity.contentViewModel.getCurrentContent().setValue(contentTextList);
    }
}
