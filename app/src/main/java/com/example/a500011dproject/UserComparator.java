package com.example.a500011dproject;

public class UserComparator implements java.util.Comparator<User> {
    public int compare(User first, User second) {
        return (first.getName().toLowerCase().compareTo(second.getName().toLowerCase()));
    }
}
