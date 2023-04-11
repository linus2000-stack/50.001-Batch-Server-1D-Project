package com.example.a500011dproject;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;

public class User implements Parcelable {
    private String name;
    HashMap<Date, String> block;

    User(String name) {
        this.name = name;
        this.block = new HashMap<>();
    }

    protected User(Parcel in) {
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
