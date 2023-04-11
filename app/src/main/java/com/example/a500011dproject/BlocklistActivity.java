package com.example.a500011dproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BlocklistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocklist);
        Bundle bundle = getIntent().getExtras();
        User user = bundle.getParcelable("USER");
    }
}