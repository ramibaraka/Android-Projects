package com.rami.osama.parkapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public final class ParkApp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park_app);
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        
        String provider = locationManager.getBestProvider(criteria, true);
      

        Location location = locationManager.getLastKnownLocation(provider);
       

        if(location != null)
        {
            MyLocationListener.latitude = location.getLatitude();
            MyLocationListener.longitude = location.getLongitude();
        }

        MyLocationListener locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(
                provider, 30000, 10, locationListener);

       


        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.parking_container);


        if(fragment == null)
        {
            fm.beginTransaction().add(R.id.parking_container, new ParkingListFragment()).commit();
        }
    }
}
