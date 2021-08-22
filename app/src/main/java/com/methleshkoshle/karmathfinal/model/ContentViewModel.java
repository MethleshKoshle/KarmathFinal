package com.methleshkoshle.karmathfinal.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.methleshkoshle.karmathfinal.model.ContentText.ContentTextList;

public class ContentViewModel extends ViewModel {

    // Create a LiveData with a String
    private MutableLiveData<ContentTextList> currentContentTextList;

    public MutableLiveData<ContentTextList> getCurrentContent() {
        if (currentContentTextList == null) {
            currentContentTextList = new MutableLiveData<ContentTextList>();
        }
        return currentContentTextList;
    }

// Rest of the ViewModel...
}