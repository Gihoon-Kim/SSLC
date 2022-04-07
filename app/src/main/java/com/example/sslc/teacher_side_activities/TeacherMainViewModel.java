package com.example.sslc.teacher_side_activities;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TeacherMainViewModel extends ViewModel {

    private final MutableLiveData<Bitmap> image = new MutableLiveData<>();
    private final MutableLiveData<String> id = new MutableLiveData<>();
    private final MutableLiveData<String> dob = new MutableLiveData<>();
    private final MutableLiveData<String> introduce = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();

    public void setImage(Bitmap bitmap) {
        image.setValue(bitmap);
    }

    public void setId(String id) { this.id.setValue(id); }
    public void setDob(String dob) { this.dob.setValue(dob); }

    public void setIntroduce(String introduce) { this.introduce.setValue(introduce); }

    public void setPassword(String password) { this.password.setValue(password); }

    public LiveData<Bitmap> getImage() {
        return image;
    }

    public MutableLiveData<String> getId() {
        return id;
    }

    public LiveData<String> getDob() {
        return dob;
    }

    public LiveData<String> getIntroduce() {
        return introduce;
    }

    public LiveData<String> getPassword() {
        return password;
    }
}
