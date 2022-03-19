package com.example.sslc.data;

public class Teacher extends People {

    int teacherId;

    boolean isTeacher;

    public Teacher(String name,
                   String image,
                   String dob,
                   String myClass,
                   String aboutMe,
                   boolean isTeacher
    ) {
        super(name, image, dob, myClass, aboutMe);
        this.isTeacher = isTeacher;
    }

    public Teacher(int teacherId,
                   String name,
                   String image,
                   String dob,
                   String myClass,
                   String aboutMe,
                   boolean isTeacher
    ) {
        super(name, image, dob, myClass, aboutMe);
        this.teacherId = teacherId;
        this.isTeacher = isTeacher;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }


    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }
}
