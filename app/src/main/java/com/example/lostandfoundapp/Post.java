package com.example.lostandfoundapp;

public class Post {
    int id;
    String type;
    String name;
    String phone;
    String description;
    String category;
    String location;
    double latitude;
    double longitude;
    String dateTime;
    String imageUri;

    public Post(int id, String type, String name, String phone, String description,
                String category, String location, double latitude, double longitude, String dateTime, String imageUri) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.category = category;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTime = dateTime;
        this.imageUri = imageUri;
    }
}