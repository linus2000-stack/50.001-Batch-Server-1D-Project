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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    int radius;
    public final static String RADIUS = "RADIUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //filesys init
        Gson gson = new GsonBuilder().setDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").create();
        File usersdir = new File(this.getFilesDir(), "users");
        File activedir = new File(this.getFilesDir(), "active.json");
        usersdir.mkdir();

        //clear user dir
//        for (File user: usersdir.listFiles()) {
//            user.delete();
//        }
//        activedir.delete();
//        Log.d("File", "All Users Cleared");

        //check for default user
        if ((usersdir.listFiles().length) == 0) {
            try {
                DefaultUser user = new DefaultUser();
                File userdir = new File(usersdir, user.getName() + ".json");
                if (userdir.createNewFile()) {
                    String jsonout = gson.toJson(user);
                    FileUtils.writeStringToFile(userdir, jsonout, "utf-8");
                    Log.i("File", "Default User created.");

                    //test user
                    try {
                        String jsonin = FileUtils.readFileToString(userdir, "utf-8");
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
        Log.d("File", "Userdir files: " + Arrays.toString(usersdir.listFiles()));

        //check for active user
        try {
            if (activedir.createNewFile()) {
                String activeout = FilenameUtils.removeExtension(usersdir.listFiles()[0].getName());
                String jsonout = gson.toJson(activeout);
                FileUtils.writeStringToFile(activedir, jsonout, "utf-8");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
    }

    @Override
    protected void onResume() {
        super.onResume();

        Gson gson = new GsonBuilder().setDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").create();

        //get active user name
        String active;
        File usersdir = new File(this.getFilesDir(), "users");
        File activedir = new File(this.getFilesDir(), "active.json");

        try {
            String jsonin = FileUtils.readFileToString(activedir, "utf-8");
            active = gson.fromJson(jsonin, String.class);
            Log.i("File", "Active User:" + active);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //get active user object
        User user;
        File userdir = new File(usersdir, active + ".json");
        try {
            String jsonin = FileUtils.readFileToString(userdir, "utf-8");
            user = gson.fromJson(jsonin, User.class);
            Log.d("File", "User object built:" + user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //buttons
        Button foodNearMeButton = findViewById(R.id.menu_button_list);
        foodNearMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toFoodNearMe = new Intent(MainActivity.this, MapsActivity.class);
                Log.i("radius" , Integer.toString(radius));
                toFoodNearMe.putExtra(RADIUS, radius);
                toFoodNearMe.putExtra("USER", user);
                startActivity(toFoodNearMe);
            }
        });

        Button myProfilesButton = findViewById(R.id.menu_button_profiles);
        myProfilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toProfiles = new Intent(MainActivity.this, UsersActivity.class);
                startActivity(toProfiles);
            }
        });

        Button blocklistButton = findViewById(R.id.menu_button_blocklist);
        blocklistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toBlocklist = new Intent(MainActivity.this, BlocklistActivity.class);
                toBlocklist.putExtra("USER", user);
                startActivity(toBlocklist);
            }
        });
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

