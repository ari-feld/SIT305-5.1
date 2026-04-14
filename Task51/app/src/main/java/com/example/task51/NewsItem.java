package com.example.task51;

public class NewsItem {
    public String title;
    public String description;
    public int imageResId;
    public String category;

    public NewsItem(String title, String description, int imageResId, String category) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
        this.category = category;
    }
}
