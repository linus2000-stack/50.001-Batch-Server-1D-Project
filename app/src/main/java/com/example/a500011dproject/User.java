package com.example.a500011dproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.HashMap;

public class User implements Parcelable {
    private String name;
    public HashMap<Date, String> block;

    private User(UserBuilder builder) {
        this.name = builder.name;
        this.block = builder.block;
    }

    protected User(Parcel in) {
        name = in.readString();
        block = (HashMap<Date, String>) in.readSerializable();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeSerializable(block);
    }

    public String getName() {
        return name;
    }

    public static class UserBuilder {
        private String name;
        private HashMap<Date, String> block;

        public UserBuilder(String name) {
            this.name = name;
        }

        public UserBuilder setOptional(HashMap<Date, String> block) {
            this.block = block;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

}
