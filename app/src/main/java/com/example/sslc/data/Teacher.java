package com.example.sslc.data;

import android.graphics.Bitmap;

public class Teacher extends People {

    int teacherNumber;

    public Teacher(
            int teacherNumber,
            String name,
            Bitmap image,
            String dob,
            String myClass,
            String aboutMe,
            boolean isTeacher
    ) {
        super(name, image, dob, myClass, aboutMe, isTeacher);

        this.teacherNumber = teacherNumber;
    }

    public int getTeacherNumber() {
        return teacherNumber;
    }

    public void setTeacherNumber(int teacherNumber) {
        this.teacherNumber = teacherNumber;
    }
}
