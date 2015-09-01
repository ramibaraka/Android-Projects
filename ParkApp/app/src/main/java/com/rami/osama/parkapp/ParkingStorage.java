package com.rami.osama.parkapp;

import android.content.Context;
import android.location.Location;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class ParkingStorage {
    private static ParkingStorage INSTANCE;
    private final Map<String, Parking> mParkings;
    private final Context mContext;
    private String phoneLat;
    private String phoneLong;

    private ParkingStorage(Context context) {
        mParkings = new HashMap<>();
        this.mContext = context;

        try {
            getParkings(100);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ParkingStorage getInstance(Context context) {
        if (INSTANCE == null)
        {
            INSTANCE = new ParkingStorage(context.getApplicationContext());
        }
        return INSTANCE;
    }

    private int radius;
    public void getParkings(final int radius) throws JSONException {
        this.radius = radius;
        final Location phoneLocation = new Location("My location");
        phoneLocation.setLatitude(MyLocationListener.latitude);
        phoneLocation.setLongitude(MyLocationListener.longitude);

        phoneLat = String.valueOf(MyLocationListener.latitude);
        phoneLong = String.valueOf(MyLocationListener.longitude);
        ParkRestClient.get("within?radius=" + radius + "&lat=" + "59.28526308" + "&lng=" + "18.06315422" + "&maxFeatures=10&outputFormat=json&apiKey=a7984ad9-3548-420b-a0d6-071ae94f462b", null, new JsonHttpResponseHandler() {

            private String streetname, id;
            private int parkingSpots;
            private double lati, longi;
            private JSONArray features, coordinates, firstCoordinates;
            private JSONObject feature, geometry, properties;

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // Tar in alla objekt (parkeringsområden) som skickats in
                try {
                    features = response.getJSONArray("features");

                    if (features.length() < 10) {
                        int newRadius = radius + 100;
                        getParkings(newRadius);
                    }

                    for (int i = 0; i < features.length(); i++) {
                        feature = (JSONObject) features.get(i);

                        id = feature.getString("id");
                        geometry = feature.getJSONObject("geometry");

                        // Sparar alla koordinater
                        coordinates = geometry.getJSONArray("coordinates");
                        firstCoordinates = coordinates.getJSONArray(0);
                        // Sparar Lat och Long från första koordinat-paret i "coordinates"
                        longi = firstCoordinates.getDouble(0);
                        lati = firstCoordinates.getDouble(1);
                        // Sparar antalet parkeringsplatser i "parkingsSpots"
                        parkingSpots = coordinates.length();
                        properties = feature.getJSONObject("properties");
                        // Sparar gatunamnet
                        streetname = properties.getString("STREET_NAME");

                        Location parkingLocation = new Location("Parking location");
                        parkingLocation.setLongitude(longi);
                        parkingLocation.setLatitude(lati);

                        int distance = (int) phoneLocation.distanceTo(parkingLocation);

                        Parking parking = new Parking(streetname, parkingSpots, lati, longi, distance, id);
                        mParkings.put(id, parking);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

             

               
        });
    }


    public List<Parking> getAllParkings() {

        return new ArrayList<Parking>(mParkings.values());
    }
}