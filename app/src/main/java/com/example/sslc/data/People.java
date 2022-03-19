package com.example.sslc.data;

public class People {

    String name;
    String image;
    String dob;
    String myClass;
    String aboutMe;

    public People(
            String name,
            String image,
            String dob,
            String myClass,
            String aboutMe
    ) {
        this.name = name;
        this.image = image;
        this.dob = dob;
        this.myClass = myClass;
        this.aboutMe = aboutMe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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
}
