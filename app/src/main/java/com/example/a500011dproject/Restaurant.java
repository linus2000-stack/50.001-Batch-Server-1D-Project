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
    private String placeId;
    private String name;
    private String address;

    private String rating;
    private String photoReference;
    public Restaurant(String placeId, String name, String address ,String rating, String photoReference) {
        this.placeId = placeId;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.photoReference = photoReference;
    }
    public String getPlaceId() {
        return placeId;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getPhotoReference() {
        return photoReference;
    }
    public String getRating() {
        return rating;
    }

    public Restaurant(Parcel in){
        this.placeId = in.readString();
        this.name = in.readString();
        this.address = in.readString();
        this.rating = in.readString();
        this.photoReference = in.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel RestaurantActivity, int i) {
        RestaurantActivity.writeArray(new Object[]{this.placeId,
                                                    this.name,
                                                    this.address,
                                                    this.rating,
                                                    this.photoReference});
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

