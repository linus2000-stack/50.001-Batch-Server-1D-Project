package com.example.a500011dproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        Gson gson = new GsonBuilder().setDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").create();
        File userdir = new File(this.getFilesDir(), "users");
        File activedir = new File(this.getFilesDir(), "active.json");
        File[] arrFiles = userdir.listFiles();
        ArrayList<User> arrUsers = new ArrayList<>();

        TextView textView = (TextView) findViewById(R.id.users_active);
        try {
            String jsonin = FileUtils.readFileToString(activedir,"utf-8");
            String users_active = "Active User:" + gson.fromJson(jsonin, String.class);
            textView.setText(users_active);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i=0; i<arrFiles.length; i++) {
            try {
                String jsonin = FileUtils.readFileToString(arrFiles[i], "utf-8");
                arrUsers.add(gson.fromJson(jsonin, User.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Log.d("Users", arrUsers.toString());

        RecyclerView rvUsers = (RecyclerView) findViewById(R.id.users_list);
        UsersAdapter adapter = new UsersAdapter(arrUsers);
        rvUsers.setAdapter(adapter);
        adapter.setOnClickListener(new UsersAdapter.OnClickListener() {
            @Override
            public void onClick(User user) {
                String jsonout = gson.toJson(user.getName());
                try {
                    FileUtils.writeStringToFile(activedir, jsonout, "utf-8");
                    Toast.makeText(UsersActivity.this, "Active User Updated", Toast.LENGTH_SHORT).show();
                    String users_active = "Active User:" + user.getName();
                    textView.setText(users_active);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        rvUsers.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton usersNewButton = findViewById(R.id.users_new_button);
        usersNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent usersToNew = new Intent(view.getContext(), NewActivity.class);
                startActivity(usersToNew);
            }
        });
    }

}