package com.example.sslc.teacher_side_activities.ui.myClassMain;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sslc.data.ClassHomework;
import com.example.sslc.data.ClassNews;
import com.example.sslc.data.Student;

public class TeacherMyClassDetailViewModel extends ViewModel {

    private final MutableLiveData<String> classTitle;
    private final MutableLiveData<ClassNews> classNewsLiveData;
    private final MutableLiveData<Student> classStudentLiveData;
    private final MutableLiveData<ClassHomework> classHomeworkLiveData;

    public TeacherMyClassDetailViewModel() {

        classNewsLiveData = new MutableLiveData<>();
        classTitle = new MutableLiveData<>();
        classStudentLiveData = new MutableLiveData<>();
        classHomeworkLiveData = new MutableLiveData<>();
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

    public LiveData<ClassHomework> getClassHomeworkLiveData() {

        return classHomeworkLiveData;
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

    public void setClassHomeworkLiveData(ClassHomework homework) {

        this.classHomeworkLiveData.setValue(homework);
    }
}
