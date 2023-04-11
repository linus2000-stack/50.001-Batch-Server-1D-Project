package com.example.a500011dproject;

import java.util.Date;

public class DefaultUser extends User{
    DefaultUser() {
        super("Default");
        Date date = new Date();
        this.block.put(date, "test restaurant");
    }

}
