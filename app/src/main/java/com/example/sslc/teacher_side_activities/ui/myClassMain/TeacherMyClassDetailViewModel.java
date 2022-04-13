package com.example.sslc.teacher_side_activities.ui.myClassMain;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sslc.data.ClassNews;

public class TeacherMyClassDetailViewModel extends ViewModel {

    private final MutableLiveData<ClassNews> classNewsLiveData;

    public TeacherMyClassDetailViewModel() {
        classNewsLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ClassNews> getClassNewsLiveData() {
        return classNewsLiveData;
    }

    public void setClassNewsLiveData(ClassNews classNews) {
        this.classNewsLiveData.setValue(classNews);
    }
}
