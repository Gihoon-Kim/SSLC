package com.example.sslc.data;

import android.graphics.Bitmap;

public class Student extends People {

    int studentID;

    public Student(
            String name,
            Bitmap image,
            String dob,
            String myClass,
            String aboutMe,
            boolean isTeacher
    ) {
        super(name, image, dob, myClass, aboutMe, isTeacher);
    }
}
