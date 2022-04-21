package com.sslc.sslc.data;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsData implements Serializable {

    private String title;
    private String description;
    private String createdAt;

    private int newsID;

    public NewsData(String title, String description) {

        this.title = title;
        this.description = description;

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        this.createdAt = dateFormat.format(date);
    }
    public NewsData(int newsID, String title, String description, String createdAt) {
        this.newsID = newsID;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public int getNewsID() {
        return newsID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
