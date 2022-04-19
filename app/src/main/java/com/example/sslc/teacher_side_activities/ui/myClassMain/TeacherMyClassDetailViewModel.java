package com.example.sslc.teacher_side_activities.ui.myClassMain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sslc.data.ClassNews;
import com.example.sslc.data.Student;

public class TeacherMyClassDetailViewModel extends ViewModel {

    private final MutableLiveData<String> classTitle;
    private final MutableLiveData<ClassNews> classNewsLiveData;
    private final MutableLiveData<Student> classStudentLiveData;

    public TeacherMyClassDetailViewModel() {

        classNewsLiveData = new MutableLiveData<>();
        classTitle = new MutableLiveData<>();
        classStudentLiveData = new MutableLiveData<>();
    }

    public LiveData<ClassNews> getClassNewsLiveData() {
        return classNewsLiveData;
    }

    public LiveData<String> getClassTitle() {
        return classTitle;
    }

    public LiveData<Student> getClassStudentLiveData() {

        return classStudentLiveData;
    }

    public void setClassNewsLiveData(ClassNews classNews) {
        this.classNewsLiveData.setValue(classNews);
    }

    public void setClassTitle(String classTitle) {
        this.classTitle.setValue(classTitle);
    }

    public void setClassStudentLiveData(Student student) {
        this.classStudentLiveData.setValue(student);
    }
}
