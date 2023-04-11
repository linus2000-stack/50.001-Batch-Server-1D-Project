package com.example.a500011dproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class RestaurantActivity extends AppCompatActivity {

    ImageView restaurant_image_view;
    TextView restaurant_name_text_view;
    TextView restaurant_address_text_view;
    TextView restaurant_phone_text_view;
    TextView restaurant_rating_text_view;
    Button restaurant_website_button;
    Button restaurant_directions_button;
    private Restaurant restaurant;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Gson gson = new GsonBuilder().setDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").create();
        File usersdir = new File(this.getFilesDir(), "users");

        // Get the intent that started this activity and extract the restaurant object from it
        Bundle bundle = getIntent().getExtras();
        Restaurant chosenRestaurant = bundle.getParcelable("chosenRestaurant");
        User user = bundle.getParcelable("USER");

        // Get chosenRestaurant's contents
        String restaurantPlaceId = chosenRestaurant.getPlaceId();
        String restaurantName = chosenRestaurant.getName();
        String restaurantAddress = chosenRestaurant.getAddress();
        String restaurantRating = chosenRestaurant.getRating();
        String restaurantPhotoReference = chosenRestaurant.getPhotoReference();

        // Set up the UI elements
        //missing placeId
        TextView restaurantNameTextView = findViewById(R.id.restaurant_name_text_view);
        restaurantNameTextView.setText(restaurantName);
        TextView restaurantAddressTextView = findViewById(R.id.restaurant_address_text_view);
        restaurantAddressTextView.setText(restaurantAddress);
        TextView restaurantRatingTextView = findViewById(R.id.restaurant_rating_text_view);
        restaurantRatingTextView.setText(restaurantRating);
        //missing photoReference
        Button directionsButton = findViewById(R.id.restaurant_directions_button);

        //NOT SURE IF WANNA DELETE (LMK?)
//        ImageView restaurantImageView = findViewById(R.id.restaurant_image_view);
        //restaurantImageView.setImageResource();
//        TextView restaurantNameTextView = findViewById(R.id.restaurant_name_text_view);
//        restaurantNameTextView .setText(restaurantName);
//        TextView restaurantAddressTextView = findViewById(R.id.restaurant_address_text_view);
//        TextView restaurantPhoneTextView = findViewById(R.id.restaurant_phone_text_view);
//        TextView restaurantRatingTextView = findViewById(R.id.restaurant_rating_text_view);
//        Button restaurantWebsiteButton = findViewById(R.id.restaurant_website_button);
//        Button directionsButton = findViewById(R.id.restaurant_directions_button);

        // Set the data for the UI elements
        //restaurantImageView.setImageResource(restaurant.getImageUrl());
        // I need to find another implementation to make an image show up in the activity_restaurant.xml
        // My URL currently is a String - setImageResource is done with integer

        // Set the click listeners for the buttons did u


        directionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                File userdir = new File(usersdir, user.getName() + ".json");
                user.block.put(date, restaurantName);
                String jsonout = gson.toJson(user);
                try {
                    FileUtils.writeStringToFile(userdir, jsonout, "utf-8");
                    Log.d("File", "User overwritten");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //TODO: Open directions to the restaurant
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.scheme("geo").opaquePart("0.0").appendQueryParameter("q", restaurantName);
                Uri uri = uriBuilder.build();
                Toast.makeText(RestaurantActivity.this, uri.toString(), Toast.LENGTH_LONG).show();
            }

//            }
//        });
        });

    }   }
