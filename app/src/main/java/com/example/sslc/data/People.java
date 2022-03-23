package com.example.sslc.data;

import android.graphics.Bitmap;

public class People {

    String name;
    Bitmap image;
    String dob;
    String myClass;
    String aboutMe;
    boolean isTeacher;

    public People(
            String name,
            Bitmap image,
            String dob,
            String myClass,
            String aboutMe,
            boolean isTeacher) {
        this.name = name;
        this.image = image;
        this.dob = dob;
        this.myClass = myClass;
        this.aboutMe = aboutMe;
        this.isTeacher = isTeacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMyClass() {
        return myClass;
    }

    public void setMyClass(String myClass) {
        this.myClass = myClass;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public void setTeacher(boolean teacher) {
        isTeacher = teacher;
    }
}
