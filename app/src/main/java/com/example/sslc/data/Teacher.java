package com.example.sslc.data;

import android.graphics.Bitmap;

public class Teacher extends People {

    int teacherNumber;

    public Teacher(
            int teacherNumber,
            String name,
            String dob,
            String myClass,
            Bitmap image,
            String aboutMe,
            boolean isTeacher
    ) {
        super(name, dob, myClass, image, aboutMe, isTeacher);

        this.teacherNumber = teacherNumber;
    }

    public int getTeacherNumber() {
        return teacherNumber;
    }
}