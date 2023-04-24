package com.example.a500011dproject;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import java.util.Objects;

public class RestaurantActivity extends AppCompatActivity {

    ImageView restaurant_open_now_image_view;
    TextView restaurant_name_text_view;
    TextView restaurant_address_text_view;
    TextView restaurant_phone_text_view;
    TextView restaurant_rating_text_view;
    TextView restaurant_price_level_text_view;
    Button restaurant_website_button;
    Button restaurant_directions_button;
    private Restaurant restaurant;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        //Button to go back
        ImageButton goBack = findViewById(R.id.details_button_back);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(RestaurantActivity.this, MapsActivity.class);
                startActivity(goBack);
            }
        });

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
        String restaurantOpenNow = chosenRestaurant.isOpenNow();
        String restaurantPriceLevel = chosenRestaurant.getPriceLevel();
        if (Objects.equals(restaurantPriceLevel, "1")) {
            restaurantPriceLevel = "$";
        } else if (Objects.equals(restaurantPriceLevel, "2")){
            restaurantPriceLevel = "$$";
        } else{
            restaurantPriceLevel = "$$$";
        }

        // Set up the UI elements
        TextView restaurantNameTextView = findViewById(R.id.restaurant_name_text_view);
        restaurantNameTextView.setText(restaurantName);
        TextView restaurantAddressTextView = findViewById(R.id.restaurant_address_text_view);
        restaurantAddressTextView.setText(restaurantAddress);
        TextView restaurantRatingTextView = findViewById(R.id.restaurant_rating_text_view);
        restaurantRatingTextView.setText(restaurantRating);
        Button directionsButton = findViewById(R.id.restaurant_directions_button);
        ImageView restaurantOpenNowImageView = findViewById(R.id.restaurant_open_now_image_view);
        if (restaurantOpenNow.equals("true")) {
            restaurantOpenNowImageView.setImageResource(R.drawable.open_sign);
        } else {
            restaurantOpenNowImageView.setImageResource(R.drawable.closed_sign);
        }
        TextView restaurant_price_level_text_view = findViewById(R.id.restaurant_price_level_text_view);
        restaurant_price_level_text_view.setText(restaurantPriceLevel);

        directionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Adds restaurant to blocklist
                Date date = new Date();
                File userdir = new File(usersdir, user.getName() + ".json");
                if (!user.block.containsValue(restaurantName)) {
                    user.block.put(date, restaurantName);
                }
                String jsonout = gson.toJson(user);
                try {
                    FileUtils.writeStringToFile(userdir, jsonout, "utf-8");
                    Log.d("File", "User overwritten");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //Opens directions to the restaurant in google maps
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.scheme("geo").opaquePart("0.0").appendQueryParameter("q", restaurantAddress);
                Uri uri = uriBuilder.build();
                Toast.makeText(RestaurantActivity.this, uri.toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                if (intent.resolveActivity(getPackageManager()) != null){
                    startActivity(intent);
                }
            }

//            }
//        });
        });

    }   }
