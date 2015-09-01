package com.rami.osama.parkapp;


import android.app.Activity;
import android.os.Bundle;

public class ParkingMap extends Activity {

    public static final String PARKING_DETAIL_ID_EXTRA = "parking.detail.id.extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_map);
    }
}