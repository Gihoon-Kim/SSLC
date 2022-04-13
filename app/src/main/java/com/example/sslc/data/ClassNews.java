package com.example.sslc.data;

public class ClassNews {

    private String newsTitle, description, classTitle, createdAt;

    public ClassNews(String newsTitle, String description, String classTitle, String createdAt) {
        this.newsTitle = newsTitle;
        this.description = description;
        this.classTitle = classTitle;
        this.createdAt = createdAt;
    }

    public ClassNews(String newsTitle, String newsDescription, String createdAt) {

        this.newsTitle = newsTitle;
        this.description = newsDescription;
        this.createdAt = createdAt;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassTitle() {
        return classTitle;
    }

    public void setClassTitle(String classTitle) {
        this.classTitle = classTitle;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
