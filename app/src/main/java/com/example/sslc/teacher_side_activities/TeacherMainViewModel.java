package com.example.sslc.teacher_side_activities;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TeacherMainViewModel extends ViewModel {

    private final MutableLiveData<Bitmap> image = new MutableLiveData<>();

    public void setImage(Bitmap bitmap) {
        image.setValue(bitmap);
    }

    public LiveData<Bitmap> getImage() {
        return image;
    }
}
