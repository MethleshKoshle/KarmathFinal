package com.methleshkoshle.karmathfinal.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.methleshkoshle.karmathfinal.model.Content.ContentTextList;

public class ContentViewModel extends ViewModel {

    private MutableLiveData<ContentTextList> currentContentTextList;

    public MutableLiveData<ContentTextList> getCurrentContent() {
        if (currentContentTextList == null) {
            currentContentTextList = new MutableLiveData<ContentTextList>();
        }
        return currentContentTextList;
    }
}