package com.example.hungryalpacas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        Gson gson = new Gson();
        File userdir = new File(this.getFilesDir(), "users");
        File[] arrFiles = userdir.listFiles();
        ArrayList<User> arrUsers = new ArrayList<>();
        for (int i=0; i<arrFiles.length; i++) {
            try {
                String userjson = FileUtils.readFileToString(arrFiles[i], "utf-8");
                arrUsers.add(gson.fromJson(userjson, User.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Log.d("Users", arrUsers.toString());

        RecyclerView rvUsers = (RecyclerView) findViewById(R.id.usersList);
        UsersAdapter adapter = new UsersAdapter(arrUsers);
        rvUsers.setAdapter(adapter);
        rvUsers.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton usersNewButton = findViewById(R.id.usersNewButton);
        usersNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent usersToNew = new Intent(view.getContext(), NewActivity.class);
                startActivity(usersToNew);
            }
        });
    }
}