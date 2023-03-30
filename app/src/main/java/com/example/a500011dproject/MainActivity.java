package com.example.a500011dproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add your initialization code here
    }

    // Define your onClick methods here

    public void foodNearMeButton(View view) {
        Intent toFoodNearMe = new Intent(MainActivity.this, MapsActivity.class);
        startActivity(toFoodNearMe);
    }

    public void myProfilesButton(View view) {
        // Add your code to handle button2 click event here
    }

    public void whereAmIButton(View view) {
        // Add your code to handle button3 click event here
    }

    // Add your other methods here
}
