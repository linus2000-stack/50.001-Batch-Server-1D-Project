package com.example.a500011dproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {
    int radius;
    public final static String RADIUS = "RADIUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add your initialization code here
    }

    // Define your onClick methods here

    public void foodNearMeButton(View view) {
        Intent toFoodNearMe = new Intent(MainActivity.this, MapsActivity.class);
        Log.i("radius" , Integer.toString(radius));
        toFoodNearMe.putExtra(RADIUS, radius);
        startActivity(toFoodNearMe);
    }

    public void myProfilesButton(View view) {
        // Add your code to handle button2 click event here
    }

    public void randomButton(View view) {
        // Add your code to handle button3 click event here
        Button button3 = (Button) findViewById(R.id.menu_button_random);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("button3", "user tapped 3rd button");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int id = item.getItemId();
        if (id == R.id.near){
            radius = 500;
        } else if (id == R.id.normal) {
            radius = 1000;
        } else if (id == R.id.far) {
            radius = 1500;
        }
        return super.onOptionsItemSelected(item);
        }
    }

