package com.example.hungryalpacas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.io.FileUtils;

import java.io.File;

import java.io.IOException;
import java.util.*;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Gson gson = new Gson();
        File userdir = new File(this.getFilesDir(), "users");
        userdir.mkdir();
        if ((userdir.listFiles().length) == 0) {
            try {
                DefaultUser user = new DefaultUser();
                File defaultuser = new File(userdir, "default" + ".json");
                if (defaultuser.createNewFile()) {
                    String jsonout = gson.toJson(user);
                    FileUtils.writeStringToFile(defaultuser, jsonout, "utf-8");
                    Log.i("File", "Default User created.");

                    //test user
                    try {
                        String jsonin = FileUtils.readFileToString(defaultuser, "utf-8");
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
        Log.d("File", "Userdir: " + Arrays.toString(userdir.listFiles()));

        //clear user dir
        for (File user: userdir.listFiles()) {
            user.delete();
        }
        Log.d("File", "All Users Cleared");

        Button mapsButton = findViewById(R.id.homeMapsButton);
        mapsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeToMaps = new Intent(view.getContext(), MapsActivity.class);
                startActivity(homeToMaps);
            }
        });

    }
}