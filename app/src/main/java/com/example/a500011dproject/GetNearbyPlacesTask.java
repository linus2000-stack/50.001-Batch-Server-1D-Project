package com.example.a500011dproject;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
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

    GoogleMap gmap;
    String latLngString;
    Location userLocation;
    int radius;
    private String photoReference;
    private Context context;
    private ArrayList<Restaurant> ListOfRestaurants = new ArrayList<>();

    public ArrayList<Restaurant> getListOfRestaurants(){
        return ListOfRestaurants;
    }

    public String getPhotoReference() {
        return photoReference;
    }
    private Marker marker;
    private User user;

    public GetNearbyPlacesTask(GoogleMap googleMap, String latLngString, Location userLocation, int radius, Context context, User user) {
        gmap = googleMap;
        this.latLngString = latLngString;
        this.userLocation = userLocation;
        this.radius = radius;
        this.context = context;
        this.user = user;
    }
    public GetNearbyPlacesTask(GoogleMap googleMap, String latLngString,Location userLocation,Context context, User user) {
        gmap = googleMap;
        this.latLngString = latLngString;
        this.userLocation = userLocation;
        this.context = context;
        this.user = user;
    }

    @Override
    protected String doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient();
        String apiKey = BuildConfig.MAPS_API_KEY;
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

        String priceLevel;

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
                String rating = result.optString("rating");
                if (result.has("price_level")) {
                    priceLevel = result.getString("price_level");
                }
                else {
                    priceLevel = "Not found";
                }
                JSONObject openNowObject = result.getJSONObject("opening_hours");
                String openNow = openNowObject.getString("open_now"); // possibly a boolean?

                //Add new Restaurant to ListOfRestaurant
                Restaurant restaurant = new Restaurant(placeId, name, address, rating, priceLevel ,openNow);
                Log.d("TAG", restaurant.toString());
                Log.d("status", restaurant.getPriceLevel());
                Log.d("status", restaurant.isOpenNow());
                if (!user.block.values().contains(name)) {
                    ListOfRestaurants.add(restaurant);
                    MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(name).snippet(address).icon(markerIcon);
                    marker = gmap.addMarker(markerOptions);
                    marker.setTag(restaurant);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getImageURL(String photoReference) {
        String apiKey = BuildConfig.MAPS_API_KEY; // Replace with your Google Maps API key
        BitmapDescriptor icon = null;

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://maps.googleapis.com/maps/api/place/photo?").newBuilder();
        urlBuilder.addQueryParameter("maxheight", "100");
        urlBuilder.addQueryParameter("photoreference", photoReference);
        urlBuilder.addQueryParameter("key", apiKey);

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder().url(url).build();

        return url;
    }

}

