package com.example.a500011dproject;

public class Restaurant {

    private String id;
    private String name;
    private String address;
    private String imageUrl;
    private String phoneNumber;
    private String rating;

    public Restaurant(String id, String name, String address, String imageUrl, String phoneNumber, String rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
        this.rating = rating;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPhoneNumber() { return phoneNumber;
    }

    public String getRating() {
        return rating;
    }
}
