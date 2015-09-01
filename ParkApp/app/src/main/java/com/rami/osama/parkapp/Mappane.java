package com.rami.osama.parkapp;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Activity;
import android.os.Bundle;

public class Mappane extends Activity implements OnMapReadyCallback {

    public static final String PARKING_DETAIL_ID_EXTRA = "parking.detail.id.extra";
    double latten;
    double longen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_map);

        latten = getIntent().getExtras().getDouble("lat");

        longen = getIntent().getExtras().getDouble("long");

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        LatLng parken = new LatLng(latten, longen);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(parken, 13));

        map.addMarker(new MarkerOptions()
                .title("Parkering")
                .snippet("Här kan du hitta parkering")
                .position(parken));
    }
}