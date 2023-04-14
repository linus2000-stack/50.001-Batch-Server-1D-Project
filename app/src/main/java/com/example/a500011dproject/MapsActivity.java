package com.example.a500011dproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationClient;
    PlacesClient placesClient;
    int requestCode = 100;
    GoogleMap gmap;
    User user;
    int radius;

    protected ArrayList<Restaurant> ListOfRestaurants = new ArrayList<Restaurant>();
    protected Restaurant chosenRestaurant;
    protected Integer chosenRestaurantIndex;

    Button randomButton;
    ImageButton map_button_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Bundle bundle = getIntent().getExtras();
        radius = bundle.getInt(MainActivity.RADIUS, 1500);
        user = bundle.getParcelable("USER");
        Log.d("User", "User:" + user.toString());

        String apiKey = BuildConfig.MAPS_API_KEY;
        Places.initialize(getApplicationContext(), apiKey);
        placesClient = Places.createClient(this);

        // Created an instance of the Fused Location Provider Client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        randomButton = (Button) findViewById(R.id.randomButton);
        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Randomiser randomiser = new Randomiser(ListOfRestaurants);
                if (ListOfRestaurants.size() > 0) {
                    //chosenRestaurantIndex = randomiser.RandomRestaurant(ListOfRestaurants);
                    //chosenRestaurant = ListOfRestaurants.get(chosenRestaurantIndex);
                    //Log.d("Restaurant", chosenRestaurant.getAddress());
                    Intent toRandomise = new Intent(MapsActivity.this, WheelActivity.class);
                    Log.d("check intent", "intent from maps to restaurant");
                    toRandomise.putParcelableArrayListExtra("List of restaurants",  ListOfRestaurants);
                    //toRandomise.putExtra("restaurantList", ListOfRestaurants);
                    toRandomise.putExtra("USER", user);
                    startActivity(toRandomise);
                } else {
                    Toast.makeText(MapsActivity.this, "Please wait for the locations to load", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gmap = googleMap;
        gmap.setOnInfoWindowClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            public void onSuccess(Location location) {
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Current Location!");
                    gmap.addMarker(markerOptions);
                    gmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    // Call AsyncTask -> moving network request to background thread due to android.os.NetworkOnMainThreadException
                    Log.d("RADIUS", Integer.toString(radius)); // this keeps logging 0 for some reason, even with the default value above
                    String latLngString = Double.toString(latLng.latitude) + "," + Double.toString(latLng.longitude);
                    GetNearbyPlacesTask getNearbyPlacesTask = new GetNearbyPlacesTask(gmap, latLngString, location,radius, MapsActivity.this, user); // should use constructor with radius to pass data
                    getNearbyPlacesTask.execute();
                    ListOfRestaurants = getNearbyPlacesTask.getListOfRestaurants();
                    Log.d("Restaurants", ListOfRestaurants.toString());
                } else {
                    Toast.makeText(MapsActivity.this, "HungryAlpacas requires your Location App Permissions", Toast.LENGTH_LONG).show();
                }
            }
        });
        //Button to go back
        ImageButton map_button_back = findViewById(R.id.map_button_back);
        map_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(goBack);
            }
        });
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        Log.d("Map", marker.toString());
        Restaurant chosenRestaurant = (Restaurant) marker.getTag();
        Intent toRestaurant = new Intent(MapsActivity.this, RestaurantActivity.class);
        Log.d("check intent", "intent from maps to restaurant");
        toRestaurant.putExtra("chosenRestaurant", chosenRestaurant);
        toRestaurant.putExtra("USER", user);
        startActivity(toRestaurant);
    }


    /**
     * Callback method that gets called when the user responds to the permissions dialog
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i("TAG", "onRequestPermissionsResults() ran");
        if (requestCode == this.requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, start using the location
                // ...
            } else {
                // Permission is denied, show an explanation or disable the functionality
                // ...
                Toast.makeText(MapsActivity.this, "HungryAlpacas has no permission to find you...", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override // May not work
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d("BACK","Back from main");
                this.finish(); // destroys current activity (CHANGE)
                return true;
            case R.id.near:       //implement variables from MAIN
                Log.d("set","NEAR");
                radius = 500;
                Log.d("radius",String.valueOf(radius));
            case R.id.normal:
                radius = 1000;
            case R.id.far:
                radius = 1500;
            }
        return super.onOptionsItemSelected(item);
    }

}