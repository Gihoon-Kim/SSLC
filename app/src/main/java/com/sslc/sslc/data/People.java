package com.sslc.sslc.data;

import android.net.Uri;

public class People {

    String name;
    String dob;
    String myClass;
    String aboutMe;
    String id;
    String password;
    Uri profileImage;
    boolean hasProfileImage = false;
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
            boolean isTeacher,
            Uri profileImage
    ) {

        this.name = name;
        this.dob = dob;
        this.myClass = myClass;
        this.aboutMe = aboutMe;
        this.hasProfileImage = hasProfileImage;
        this.isTeacher = isTeacher;
        this.profileImage = profileImage;
    }

    public People(
            String name,
            String dob,
            String aboutMe,
            boolean hasProfileImage,
            Uri imageUri
    ) {

        this.name = name;
        this.dob = dob;
        this.aboutMe = aboutMe;
        this.hasProfileImage = hasProfileImage;
        this.profileImage = imageUri;
    }

    public People(
            String name,
            String dob,
            String myClass,
            String aboutMe,
            String id,
            String password,
            boolean hasProfileImage,
            boolean isTeacher,
            Uri profileImageUri
    ) {

        this.name = name;
        this.dob = dob;
        this.myClass = myClass;
        this.aboutMe = aboutMe;
        this.id = id;
        this.password = password;
        this.hasProfileImage = hasProfileImage;
        this.isTeacher = isTeacher;
        this.profileImage = profileImageUri;
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

    public void setHasProfileImage(boolean hasProfileImage) {

        this.hasProfileImage = hasProfileImage;
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

    public Uri getProfileImage() {
        return profileImage;
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

    public void setProfileImage(Uri profileImage) {
        this.profileImage = profileImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
