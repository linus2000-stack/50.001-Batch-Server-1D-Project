package com.example.a500011dproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

public class BlocklistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocklist);
        ArrayList<Date> arrDates = new ArrayList<>();
        ArrayList<String> arrPlaces = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        User user = bundle.getParcelable("USER");
        Log.d("User", "Blocklist loaded: " + user.block.toString());
        arrDates = new ArrayList<>(user.block.keySet());
        Collections.sort(arrDates, new DateComparator());
        for (Date date: arrDates) {
            arrPlaces.add(user.block.get(date));
        }
        for (int i = 0; i < arrDates.size(); i++){

        }


        RecyclerView rvBlock = (RecyclerView) findViewById(R.id.block_list);
        BlockAdapter adapter = new BlockAdapter(arrDates, arrPlaces);
        rvBlock.setAdapter(adapter);
        rvBlock.setLayoutManager(new LinearLayoutManager(this));


    }
}