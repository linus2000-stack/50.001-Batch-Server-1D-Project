package com.example.a500011dproject;

import android.service.notification.NotificationListenerService;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class Randomiser{
    private  ArrayList<Restaurant> ListOfRestaurants;
    public Randomiser(ArrayList<Restaurant> ListOfRestaurants){
        this.ListOfRestaurants = ListOfRestaurants;
    }
    public ArrayList<Restaurant> getListOfRestaurants(){
        return ListOfRestaurants;
    }

    public Restaurant RandomRestaurant(ArrayList<Restaurant> ListOfRestaurants){
        //create random object
        Random random = new Random();
        //generate random index less than size of array
        Log.i("size" , Integer.toString(ListOfRestaurants.size())); //
        int index = random.nextInt(ListOfRestaurants.size());
        // obtain object Restaurant of chosen index
        Restaurant chosenRestaurant = ListOfRestaurants.get(index);
        Log.d("chosenRestaurant", chosenRestaurant.getName());
        return chosenRestaurant;

    }
}
