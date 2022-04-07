package com.example.sslc.teacher_side_activities.ui.home;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Bitmap> image;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        image = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Bitmap> getImage() { return image; }

    public void setImage(Bitmap bitmap) {
        this.image.setValue(bitmap);
    }
}