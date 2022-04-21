package com.sslc.sslc.data;

public class Teacher extends People {

    int teacherNumber;

    public Teacher(
            int teacherNumber,
            String name,
            String dob,
            String myClass,
            String aboutMe,
            boolean hasProfileImage,
            boolean isTeacher
    ) {
        super(name, dob, myClass, aboutMe, isTeacher, hasProfileImage);

        this.teacherNumber = teacherNumber;
    }

    public int getTeacherNumber() {
        return teacherNumber;
    }
}