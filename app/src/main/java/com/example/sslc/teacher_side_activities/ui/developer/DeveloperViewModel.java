package com.example.sslc.teacher_side_activities.ui.developer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DeveloperViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DeveloperViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}