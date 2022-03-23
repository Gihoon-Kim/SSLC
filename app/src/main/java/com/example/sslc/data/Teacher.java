package com.example.sslc.data;

import android.graphics.Bitmap;

public class Teacher extends People {

    int teacherId;

    public Teacher(String name,
                   Bitmap image,
                   String dob,
                   String myClass,
                   String aboutMe,
                   boolean isTeacher
    ) {
        super(name, image, dob, myClass, aboutMe, isTeacher);
        this.isTeacher = isTeacher;
    }

    public Teacher(int teacherId,
                   String name,
                   Bitmap image,
                   String dob,
                   String myClass,
                   String aboutMe,
                   boolean isTeacher
    ) {
        super(name, image, dob, myClass, aboutMe, isTeacher);
        this.teacherId = teacherId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
