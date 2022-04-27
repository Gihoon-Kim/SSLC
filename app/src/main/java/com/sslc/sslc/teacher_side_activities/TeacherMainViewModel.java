package com.sslc.sslc.teacher_side_activities;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sslc.sslc.data.Teacher;

public class TeacherMainViewModel extends ViewModel {

    private final MutableLiveData<Teacher> teacherInformation = new MutableLiveData<>();

    public LiveData<Teacher> getTeacherInformation() {
        return teacherInformation;
    }

    public void setTeacherInformation(Teacher teacher) {
        teacherInformation.setValue(teacher);
    }
}
