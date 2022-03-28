package com.example.sslc.data;

import android.app.Application;

import java.util.ArrayList;

public class AppData extends Application {

    private ArrayList<String> classList = new ArrayList<>();

    public ArrayList<String> getClassList() {
        return classList;
    }

    public void setClassList(ArrayList<String> classList) {
        this.classList = classList;
    }
}
