package com.example.sslc.data;

public class NewsData {

    private String title;
    private String description;
    private String createdAt;

    private int newsID;

    public NewsData(int newsID, String title, String description, String createdAt) {
        this.newsID = newsID;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
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
