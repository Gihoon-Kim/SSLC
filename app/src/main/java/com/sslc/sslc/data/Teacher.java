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
            Uri profileImageUri)
    {
        super(name, dob, myClass, aboutMe, hasProfileImage, isTeacher, profileImageUri);

        this.teacherNumber = teacherNumber;
    }

    public Teacher(
            String name,
            String dob,
            String myClass,
            String aboutMe,
            String id,
            String password,
            boolean hasProfileImage,
            boolean isTeacher,
            Uri profileImageUri)
    {
        super(name, dob, myClass, aboutMe, id, password, hasProfileImage, isTeacher, profileImageUri);
    }

    public int getTeacherNumber() {
        return teacherNumber;
    }
}