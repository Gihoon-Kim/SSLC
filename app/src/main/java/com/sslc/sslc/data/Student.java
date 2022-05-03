package com.sslc.sslc.data;

import android.net.Uri;

public class Student extends People {

    int studentNumber;
    String studentCountry;

    public Student(
            int studentNumber,
            String studentName,
            String studentDOB,
            String studentClass,
            boolean isTeacher,
            String studentIntroduce
    ) {
        super(studentName, studentDOB, studentClass, isTeacher, studentIntroduce);

        this.studentNumber = studentNumber;
    }

    public Student(
            int studentNumber,
            String studentName,
            String studentDOB,
            String studentClass,
            boolean isTeacher,
            String studentIntroduce,
            String studentCountry
    ) {
        super(studentName, studentDOB, studentClass, isTeacher, studentIntroduce);

        this.studentNumber = studentNumber;
        this.studentCountry = studentCountry;
    }

    public Student(
            String studentName,
            String studentDOB,
            String studentIntroduce,
            String studentCountry,
            boolean hasProfileImage,
            Uri imageUri
    ) {
        super(studentName, studentDOB, studentIntroduce, hasProfileImage, imageUri);

        this.studentCountry = studentCountry;
    }

    public Student(
            String studentName,
            String studentDOB,
            String studentClass,
            String studentIntroduce,
            String studentID,
            String studentPassword,
            boolean hasProfileImage,
            String country,
            boolean isTeacher,
            Uri profileImageUri
    ) {
        super(studentName, studentDOB, studentClass, studentIntroduce, studentID, studentPassword, hasProfileImage, isTeacher, profileImageUri);

        this.studentCountry = country;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public String getStudentCountry() {
        return studentCountry;
    }

    public void setStudentCountry(String country) {

        this.studentCountry = country;
    }
}