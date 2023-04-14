package com.example.a500011dproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;

public class WheelActivity extends AppCompatActivity {
    private LuckyWheel wheel;
    private List<WheelItem> wheelItems ;
    protected Restaurant chosenRestaurant;
    protected Integer chosenRestaurantIndex;
    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);



        Bundle bundle = getIntent().getExtras();
        User user = bundle.getParcelable("USER");
        ArrayList<Restaurant> ListOfRestaurants = getIntent().getParcelableArrayListExtra("List of restaurants");
        Randomiser randomiser = new Randomiser(ListOfRestaurants);
        ArrayList<String> colorList = generateColorList();
        generateWheelItems(ListOfRestaurants, colorList);
        wheel = findViewById(R.id.wheel);
        wheel.addWheelItems(wheelItems);

        wheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                Intent toDetails = new Intent(WheelActivity.this, RestaurantActivity.class);
                toDetails.putExtra("chosenRestaurant", chosenRestaurant);
                toDetails.putExtra("USER", user);
                startActivity(toDetails);
            }
        });

        Button start = findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chosenRestaurantIndex = randomiser.RandomRestaurant(ListOfRestaurants);
                chosenRestaurant = ListOfRestaurants.get(chosenRestaurantIndex);
                Log.d("CHOSEN", chosenRestaurant.getName());
                wheel.rotateWheelTo(chosenRestaurantIndex+1); // chosen restaurant
            }
        });
    }

    private void generateWheelItems(ArrayList<Restaurant> ListOfRestaurants, ArrayList<String> colorList) {
        wheelItems = new ArrayList<>();
        int r = ListOfRestaurants.size();
        int c = colorList.size();
        /*for (Restaurant restaurant : ListOfRestaurants){
            wheelItems.add(new WheelItem(Color.parseColor(String.valueOf(R.color.pastel_red)),restaurant.getName()));
        }*/
        Log.d("ADDING","starting add");
        for (int i =0 ; i < r ; i++) {
            String restaurantName = ListOfRestaurants.get(i).getName();
            String color = colorList.get(i%c);
            wheelItems.add(new WheelItem(Color.parseColor(color),restaurantName));
        }
    }

    @SuppressLint("ResourceAsColor")
    private ArrayList<String> generateColorList(){
        ArrayList<String> colorList = new ArrayList<>();
        colorList.add("#fc6c6c");
        colorList.add("#00E6FF");
        colorList.add("#F00E6F");
        return colorList;
    }
}
