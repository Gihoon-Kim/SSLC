package com.example.sslc.data;

import android.graphics.Bitmap;

public class Student extends People {

    int studentNumber;
    String studentCountry;

    public Student(
            int studentNumber,
            String name,
            Bitmap image,
            String dob,
            String myClass,
            String aboutMe,
            String studentCountry,
            boolean isTeacher
    ) {
        super(name, image, dob, myClass, aboutMe, isTeacher);

        this.studentNumber = studentNumber;
        this.studentCountry = studentCountry;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getStudentCountry() {
        return studentCountry;
    }

    public void setStudentCountry(String studentCountry) {
        this.studentCountry = studentCountry;
    }
}
