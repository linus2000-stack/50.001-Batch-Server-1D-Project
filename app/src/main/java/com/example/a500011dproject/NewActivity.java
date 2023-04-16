package com.example.a500011dproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;

public class NewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Gson gson = new GsonBuilder().setDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").create();
        File userdir = new File(this.getFilesDir(), "users");
        Button newUsersButtonConfirm = findViewById(R.id.new_confirm);
        newUsersButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText nameEdit = (EditText) findViewById(R.id.new_name_edit_text);
                String name = nameEdit.getText().toString();

                if (name.length() != 0) {
                    User user = new User.UserBuilder(name).build();
                    File userwrite = new File(userdir, user.getName() + ".json");
                    String userjson = gson.toJson(user);
                    try {
                        FileUtils.writeStringToFile(userwrite, userjson, "utf-8");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                Intent newToUsers = new Intent(view.getContext(), UsersActivity.class);
                startActivity(newToUsers);
            }
        });

        Button newUsersButtonCancel = findViewById(R.id.new_cancel);
        newUsersButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newToUsers = new Intent(view.getContext(), UsersActivity.class);
                startActivity(newToUsers);
            }
        });
    }
}