package com.example.a500011dproject;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MapsActivity extends AppCompatActivity {
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationClient;
    int requestCode = 100;
    Location userLocation;
    GoogleMap googleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        String apiKey = BuildConfig.MAPS_API_KEY;
        Places.initialize(getApplicationContext(), apiKey);
        PlacesClient placesClient = Places.createClient(this);


        // Created an instance of the Fused Location Provider Client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

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
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    public void onSuccess(Location location) {
                        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(@NonNull GoogleMap googleMap) {
                                if (location != null) {
                                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                                    MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Current Location!");
                                    googleMap.addMarker(markerOptions);
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                                    // Updates global variable of the user location
                                    userLocation = location;

                                    // Defines the place fields to be returned
                                    List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS,Place.Field.TYPES);



                                    FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);
                                    if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        return;
                                    }
                                    Task<FindCurrentPlaceResponse> placeResponse = placesClient.findCurrentPlace(request);

                                    placeResponse.addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            FindCurrentPlaceResponse response = task.getResult();
                                            for (PlaceLikelihood placeLikelihood : response.getPlaceLikelihoods()) {
                                                Log.i("TAG" , "This ran");
                                                Place place = placeLikelihood.getPlace();
                                                LatLng placeLatLng = place.getLatLng();

                                                // filter by types
                                                List<Place.Type> placeTypes = place.getTypes();
                                                if (placeTypes.contains(Place.Type.MEAL_DELIVERY) ||
                                                        placeTypes.contains(Place.Type.CAFE) ||
                                                        placeTypes.contains(Place.Type.MEAL_TAKEAWAY) ||
                                                        placeTypes.contains(Place.Type.BAR) ||
                                                        placeTypes.contains(Place.Type.RESTAURANT) ||
                                                        // school is not needed but used to debug
                                                        placeTypes.contains(Place.Type.SCHOOL)) {
                                                    MarkerOptions placesMarkerOptions = new MarkerOptions()
                                                            .position(placeLatLng)
                                                            .title(place.getName())
                                                            .snippet(place.getAddress());
                                                    googleMap.addMarker(placesMarkerOptions);
                                                }
                                            }
                                        } else{
                                            ApiException exception = (ApiException) task.getException();
                                            Log.e("MapsActivity", "Place not found: " + exception.getMessage());
                                        }
                                    });
                                } else {
                                    Toast.makeText(MapsActivity.this, "Please turn on your Location App Permissions", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
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
                Toast.makeText(MapsActivity.this, "HungryAlpacas can't find you...", Toast.LENGTH_LONG).show();
            }
        }
    }
}