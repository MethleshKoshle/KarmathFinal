package com.methleshkoshle.karmathfinal.model;

public class ContentCard {
    private int mImageResource;
    private String mContent;
    private Boolean mFavorite;

    public ContentCard(int imageResource, String content, Boolean state){
        mImageResource=imageResource;
        mContent=content;
        mFavorite=state;
    }

    public  int getImageResource(){
        return  mImageResource;
    }

    public String getContent() {
        return mContent;
    }

    public Boolean getState() {
        return mFavorite;
    }
}
