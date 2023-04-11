package com.example.a500011dproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Restaurant implements Parcelable {

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
    public String getAddress() { return address;}
    public String getPhotoReference() {
        return photoReference;
    }
    public String getRating() {
        return rating;
    }


    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }
        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
    protected Restaurant(Parcel in){
        placeId = in.readString();
        name = in.readString();
        address = in.readString();
        rating = in.readString();
        photoReference = in.readString();
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(placeId);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(rating);
        dest.writeString(photoReference);
    }
}


