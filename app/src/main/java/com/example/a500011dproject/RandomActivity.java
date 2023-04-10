package com.example.a500011dproject;

import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Random;

public class RandomActivity {
    //GetNearbyPlacesTask newArray = new GetNearbyPlacesTask();
    //ArrayList<Restaurant> ListOfRestaurants =  newArray.getListOfRestaurants();
    ArrayList<Restaurant> ListOfRestaurants =  ;

    private Restaurant RandomRestaurant(ArrayList<Restaurant>){
        //create random object
        Random random = new Random();
        //generate random index, index <= size of ArrayList
        int index = random.nextInt(ListOfRestaurants.size());
        //Restaurant from the selected random index
        Restaurant chosenRestaurant = ListOfRestaurants.get(index);
        return chosenRestaurant;
    }
    public Restaurant getRandomRestaurant(Restaurant chosenRestaurant) {
        return chosenRestaurant;
    }
}
