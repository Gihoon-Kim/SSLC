package com.sslc.sslc.data;

import android.net.Uri;

public class Teacher extends People {

    int teacherNumber;

    public Teacher(
            int teacherNumber,
            String name,
            String dob,
            String myClass,
            String aboutMe,
            boolean hasProfileImage,
            boolean isTeacher,
            Uri profileImageUri) {
        super(name, dob, myClass, aboutMe, hasProfileImage, isTeacher);

        this.teacherNumber = teacherNumber;
    }

    public int getTeacherNumber() {
        return teacherNumber;
    }
}