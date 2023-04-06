package com.example.a500011dproject;

import java.util.ArrayList;
import java.util.Random;

public class RandomActivity {

    public String getRandomRestaurant(ArrayList<String> list) {
        //create random object
        Random rand = new Random();
        //generate random index less than size of array
        int index = rand.nextInt(list.size());
        //return string of chosen index
        return list.get(index);
    }

    public static void main(String[] args) {
        // Hardcoded arraylist , will replace with user's restaurants
        ArrayList<String> RestaurantArray = new ArrayList<String>();
        RestaurantArray.add("Macdonalds");
        RestaurantArray.add("KFC");
        RestaurantArray.add("Haidilao");
        System.out.println(RestaurantArray);
        //get the randomly selected restaurant
        RandomActivity chosenRestaurant = new RandomActivity();
        System.out.println(chosenRestaurant.getRandomRestaurant(RestaurantArray));
        //return chosenRestaurant.getRandomRestaurant(RestaurantArray);
    }

}
