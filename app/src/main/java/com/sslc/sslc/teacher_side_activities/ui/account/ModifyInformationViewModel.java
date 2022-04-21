package com.sslc.sslc.teacher_side_activities.ui.account;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ModifyInformationViewModel extends ViewModel {

    private final MutableLiveData<String> name;
    private final MutableLiveData<String> dob;
    private final MutableLiveData<String> introduce;
    private final MutableLiveData<Bitmap> image;

    public ModifyInformationViewModel() {

        name = new MutableLiveData<>();
        dob = new MutableLiveData<>();
        introduce = new MutableLiveData<>();
        image = new MutableLiveData<>();
    }

    public LiveData<String> getName() {
        return name;
    }

    public LiveData<String> getDOB() {
        return dob;
    }

    public LiveData<String> getIntroduce() {
        return introduce;
    }

    public LiveData<Bitmap> getImage() {
        return image;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setDob(String dob) {
        this.dob.setValue(dob);
    }

    public void setIntroduce(String introduce) {
        this.introduce.setValue(introduce);
    }

    public void setImage(Bitmap bitmap) {
        this.image.setValue(bitmap);
    }
}