package com.example.a500011dproject;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class BlockList {
    private List<VisitedRestaurant> visitedRestaurants;


    public BlockList(List<VisitedRestaurant> visitedRestaurants) {
        this.visitedRestaurants = new ArrayList<>(visitedRestaurants);
    }

    // android studio added this line of code below @RequiresAPi
    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<String> getBlockedRestaurants(int x) {
        LocalDate currentDate = LocalDate.now();

        List<String> blockedRestaurants = new ArrayList<>();

        // Sort the visitedRestaurants list by visit date
        visitedRestaurants.sort(Comparator.comparing(VisitedRestaurant::getVisitDate));

        // Iterate through the sorted visitedRestaurants list
        for (VisitedRestaurant visitedRestaurant : visitedRestaurants) {
            // Calculate the number of days between the visit date and the current date
            long daysDifference = ChronoUnit.DAYS.between(visitedRestaurant.getVisitDate(), currentDate);
            // If the number of days is less than x, add the restaurant and visit date to the blockedRestaurants list
            if (daysDifference < x) {
                blockedRestaurants.add(visitedRestaurant.getName() + " - " + visitedRestaurant.getVisitDate());
            }
        }

        return blockedRestaurants;
    }

    public void removeRestaurantFromBlockList(String restaurantName) {
        Iterator<VisitedRestaurant> iterator = visitedRestaurants.iterator();

        while (iterator.hasNext()) {
            VisitedRestaurant visitedRestaurant = iterator.next();
            if (visitedRestaurant.getName().equalsIgnoreCase(restaurantName)) {
                iterator.remove();
            }
        }
    }

    // android studio added this line of code below @RequiresAPi
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {
        List<VisitedRestaurant> visitedRestaurants = new ArrayList<>();
        visitedRestaurants.add(new VisitedRestaurant("KFC", LocalDate.of(2023, 4, 2)));
        visitedRestaurants.add(new VisitedRestaurant("Burger King", LocalDate.of(2023, 3, 31)));
        visitedRestaurants.add(new VisitedRestaurant("PizzaHut", LocalDate.of(2023, 3, 25)));

        BlockList blockList = new BlockList(visitedRestaurants);
        int x = 10;

        // code line before correction: List<String> blockedRestaurants = blockList.getBlockedRestaurants(x);
        List<String> blockedRestaurants = blockList.getBlockedRestaurants(x);
        System.out.println("Blocked restaurants within " + x + " days:");
        blockedRestaurants.forEach(System.out::println);

        // Remove a restaurant from the block list
        blockList.removeRestaurantFromBlockList("Burger King");
        System.out.println("\nBlocked restaurants after removing Burger King:");
        blockedRestaurants = blockList.getBlockedRestaurants(x);
        blockedRestaurants.forEach(System.out::println);
    }
}


class VisitedRestaurant {
    private String name;
    private LocalDate visitDate;

    public VisitedRestaurant(String name, LocalDate visitDate) {
        this.name = name;
        this.visitDate = visitDate;
    }

    public String getName() {
        return name;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }
}

