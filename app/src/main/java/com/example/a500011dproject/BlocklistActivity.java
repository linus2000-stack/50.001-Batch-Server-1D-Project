package com.example.a500011dproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.Date;
import java.util.HashMap;

public class BlocklistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocklist);
        Bundle bundle = getIntent().getExtras();
        User user = bundle.getParcelable("USER");
        Log.d("User", "Blocklist loaded: " + user.block.toString());

        RecyclerView rvBlock = (RecyclerView) findViewById(R.id.block_list);
        BlockAdapter adapter = new BlockAdapter(user.block);
        rvBlock.setAdapter(adapter);
        rvBlock.setLayoutManager(new LinearLayoutManager(this));
    }
}