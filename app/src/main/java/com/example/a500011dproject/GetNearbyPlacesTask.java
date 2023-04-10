package com.example.a500011dproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetNearbyPlacesTask extends AsyncTask<Void, Void, String> {

    GoogleMap googleMap;
    String latLngString;
    Location userLocation;
    int radius=1500;
    private String photoReference;
    private Context context;
    private ArrayList<Restaurant> ListOfRestaurants = new ArrayList<>();

    public ArrayList<Restaurant> getListOfRestaurants(){
        return ListOfRestaurants;
    }


    public String getPhotoReference() {
        return photoReference;
    }

    public GetNearbyPlacesTask(GoogleMap googleMap, String latLngString, Location userLocation, int radius, Context context) {
        this.googleMap = googleMap;
        this.latLngString = latLngString;
        this.userLocation = userLocation;
        this.radius = radius;
        this.context = context;
    }
    public GetNearbyPlacesTask(GoogleMap googleMap, String latLngString,Location userLocation,Context context) {
        this.googleMap = googleMap;
        this.latLngString = latLngString;
        this.userLocation = userLocation;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        String apiKey = BuildConfig.MAPS_API_KEY; // Replace with your Google Maps API key
        String types = "restaurant|cafe|meal_delivery|meal_takeway|bakery";

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://maps.googleapis.com/maps/api/place/nearbysearch/json").newBuilder();
        urlBuilder.addQueryParameter("location", latLngString);
        urlBuilder.addQueryParameter("radius", String.valueOf(radius));
        urlBuilder.addQueryParameter("type", types);
        urlBuilder.addQueryParameter("key", apiKey);

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String responseBody) {
        super.onPostExecute(responseBody);

        // Parse the JSON response
        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray results = jsonObject.getJSONArray("results");

            // Check for json response within the logcat
            Log.d("TAG", responseBody);

            // Change location markers to desired colour
            BitmapDescriptor markerIcon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);

            // Add markers for each place on a Google Map
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                JSONObject locationObject = result.getJSONObject("geometry").getJSONObject("location");
                double latitude = locationObject.getDouble("lat");
                double longitude = locationObject.getDouble("lng");
                LatLng latLng = new LatLng(latitude, longitude);
                String name = result.getString("name");
                String address = result.getString("vicinity");
                String placeId = result.getString("place_id");
                //Add new Restaurant to ListOfRestaurant
                //TODO Fix the naming of restaurant
                Restaurant restaurant = new Restaurant(name, address, placeId, "imageurl", "phone number", "rating");
                ListOfRestaurants.add(restaurant);

                // Get the photos of the restaurant using the Place ID
                OkHttpClient client = new OkHttpClient();
                String apiKey = BuildConfig.MAPS_API_KEY; // Replace with your Google Maps API key

                HttpUrl.Builder urlBuilder = HttpUrl.parse("https://maps.googleapis.com/maps/api/place/details/json").newBuilder();
                urlBuilder.addQueryParameter("placeid", placeId);
                urlBuilder.addQueryParameter("fields", "photos");
                urlBuilder.addQueryParameter("key", apiKey);

                String url = urlBuilder.build().toString();
                Request request = new Request.Builder().url(url).build();

                try (Response response = client.newCall(request).execute()) {
                    String responseBodyDetails = response.body().string();
                    JSONObject jsonObjectDetails = new JSONObject(responseBodyDetails);
                    JSONObject resultDetails = jsonObjectDetails.getJSONObject("result");
                    JSONArray photosArray = resultDetails.getJSONArray("photos");

                    // Get the first photo of the restaurant and set it as the marker icon
                    if (photosArray.length() > 0) {
                        JSONObject photoObject = photosArray.getJSONObject(0);
                        String photoReference = photoObject.getString("photo_reference");
                        this.photoReference = photoReference;
                        BitmapDescriptor photoIcon = getPhotoIcon(photoReference);
                        if (photoIcon != null) {
                            markerIcon = photoIcon;
                        }


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(name).snippet(address).icon(markerIcon);
                googleMap.addMarker(markerOptions);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private BitmapDescriptor getPhotoIcon(String photoReference) {
        OkHttpClient client = new OkHttpClient();
        String apiKey = BuildConfig.MAPS_API_KEY; // Replace with your Google Maps API key
        BitmapDescriptor icon = null;

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://maps.googleapis.com/maps/api/place/photo").newBuilder();
        urlBuilder.addQueryParameter("maxheight", "100");
        urlBuilder.addQueryParameter("photoreference", photoReference);
        urlBuilder.addQueryParameter("key", apiKey);

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            icon = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeStream(response.body().byteStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return icon;
    }

}

