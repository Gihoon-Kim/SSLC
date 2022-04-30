package com.sslc.sslc.student_side_activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sslc.sslc.data.Student;

public class StudentMainViewModel extends ViewModel {

    private final MutableLiveData<Student> studentInformation = new MutableLiveData<>();

    public LiveData<Student> getStudentInformation() {
        return studentInformation;
    }

    public void setStudentInformation(Student student) {
        studentInformation.setValue(student);
    }
}
