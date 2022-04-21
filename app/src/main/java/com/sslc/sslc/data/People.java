package com.sslc.sslc.data;

public class People {

    String name;
    String dob;
    String myClass;
    String aboutMe;
    boolean hasProfileImage;
    boolean isTeacher;

    public People(
            String name,
            String dob,
            String myClass,
            boolean isTeacher,
            String introduce
    ) {
        this.name = name;
        this.dob = dob;
        this.myClass = myClass;
        this.isTeacher = isTeacher;
        this.aboutMe = introduce;
    }

    public People(
            String name,
            String dob,
            String myClass,
            String aboutMe,
            boolean hasProfileImage,
            boolean isTeacher
    ) {

        this.name = name;
        this.dob = dob;
        this.myClass = myClass;
        this.aboutMe = aboutMe;
        this.hasProfileImage = hasProfileImage;
        this.isTeacher = isTeacher;
    }

    public People(
            String name,
            String dob,
            String aboutMe,
            boolean hasProfileImage
    ) {

        this.name = name;
        this.dob = dob;
        this.aboutMe = aboutMe;
        this.hasProfileImage = hasProfileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasProfileImage() {
        return hasProfileImage;
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

    public void setHasProfileImage(boolean hasProfileImage) {
        this.hasProfileImage = hasProfileImage;
    }

}
