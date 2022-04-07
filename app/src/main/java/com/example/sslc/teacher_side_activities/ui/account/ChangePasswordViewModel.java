package com.example.sslc.teacher_side_activities.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChangePasswordViewModel extends ViewModel {

    private final MutableLiveData<String> mError;

    public ChangePasswordViewModel() {
        mError = new MutableLiveData<>();
    }

    public LiveData<String> getError() {
        return mError;
    }

    public void setError(String error) {
        mError.setValue(error);
    }
}
