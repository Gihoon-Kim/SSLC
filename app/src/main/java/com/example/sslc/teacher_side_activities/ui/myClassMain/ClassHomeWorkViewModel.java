package com.example.sslc.teacher_side_activities.ui.myClassMain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClassHomeWorkViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ClassHomeWorkViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText.setValue(mText);
    }
}