package com.bluehomestudio.luckywheel;

import android.graphics.Bitmap;


/**
 * Created by mohamed on 22/04/17.
 */

public class WheelItem {

    public int color;

    public String name;

        public WheelItem(int color) {
        this.color = color;
    }

    public WheelItem(int color, String name) {
        this.color = color;
        this.name = name;
    }

}
