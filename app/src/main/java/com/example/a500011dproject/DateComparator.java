package com.example.a500011dproject;

import java.util.Date;

public class DateComparator implements java.util.Comparator<Date> {
    public int compare(Date first, Date second) {
        if (first.after(second)) {
            return 1;
        }
        else if  (first.equals(second)) {
            return 0;
        }
        else {
            return -1;
        }
    }
}
