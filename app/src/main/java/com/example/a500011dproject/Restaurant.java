package com.example.a500011dproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Restaurant implements Parcelable {

    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        public Restaurant[] newArray(int size) {
            return new Restaurant[size];

        }
    };
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

    public Restaurant(Parcel in){
        this.id = in.readString();
        this.address = in.readString();
        this.imageUrl = in.readString();
        this.phoneNumber = in.readString();
        this.rating = in.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel RestaurantActivity, int i) {
        RestaurantActivity.writeArray(new Object[]{this.id,
                                                    this.address,
                                                    this.imageUrl,
                                                    this.phoneNumber,
                                                    this.rating});
    }

}
//    private String id;
//    private String name;
//    private String address;
//    private String imageUrl;
//    private String phoneNumber;
//    private String rating;

//    public Restaurant(String id, String name, String address, String imageUrl, String phoneNumber, String rating) {
//        this.id = id;
//        this.name = name;
//        this.address = address;
//        this.imageUrl = imageUrl;
//        this.phoneNumber = phoneNumber;
//        this.rating = rating;
//
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public String getPhoneNumber() { return phoneNumber;
//    }
//
//    public String getRating() {
//        return rating;
//    }


//}

