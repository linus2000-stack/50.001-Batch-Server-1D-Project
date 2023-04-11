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

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    int radius;
    public final static String RADIUS = "RADIUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //filesys init
        Gson gson = new Gson();
        File userdir = new File(this.getFilesDir(), "users");
        userdir.mkdir();

        //clear user dir
//        for (File user: userdir.listFiles()) {
//            user.delete();
//        }
//        Log.d("File", "All Users Cleared");

        if ((userdir.listFiles().length) == 0) {
            try {
                DefaultUser user = new DefaultUser();
                File defaultuserwrite = new File(userdir, user.getName() + ".json");
                if (defaultuserwrite.createNewFile()) {
                    String jsonout = gson.toJson(user);
                    FileUtils.writeStringToFile(defaultuserwrite, jsonout, "utf-8");
                    Log.i("File", "Default User created.");

                    //test user
                    try {
                        String jsonin = FileUtils.readFileToString(defaultuserwrite, "utf-8");
                        Log.d("File", "Default User Test: " + jsonin);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            catch (IOException e) {
                Log.e("File", "Default User creation IO Error");
                throw new RuntimeException(e);
            }
        }
        Log.d("File", "Userdir files: " + Arrays.toString(userdir.listFiles()));

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.near:       //implement variables from MAIN
                        radius = 500;
                    case R.id.normal:
                        radius = 1000;
                    case R.id.far:
                        radius = 1500;
                }
                return false;
            }
        });

        //buttons
        Button foodNearMeButton = findViewById(R.id.menu_button_list);
        foodNearMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toFoodNearMe = new Intent(MainActivity.this, MapsActivity.class);
                Log.i("radius" , Integer.toString(radius));
                toFoodNearMe.putExtra(RADIUS, radius);
                startActivity(toFoodNearMe);
            }
        });

        Button blocklistButton = findViewById(R.id.menu_button_blocklist);
        blocklistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });

        Button myProfilesButton = findViewById(R.id.menu_button_profiles);
        myProfilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
            }
        });

    }


    public void whereAmIButton(View view) {
        // Add your code to handle button3 click event here
    }

    /*
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
        */
    }

