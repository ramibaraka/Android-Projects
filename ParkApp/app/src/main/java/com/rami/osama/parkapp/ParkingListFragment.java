package com.rami.osama.parkapp;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;


public final class ParkingListFragment extends ListFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        List<Parking> parkings = ParkingStorage.getInstance(getActivity()).getAllParkings();


        ParkingListAdapter parkingListAdapter = new ParkingListAdapter(getActivity(), parkings);

        setListAdapter(parkingListAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Parking parking = (Parking) getListAdapter().getItem(position);
        Intent intent = new Intent(getActivity(), Mappane.class);
        intent.putExtra("lat", parking.getLatitude());
        intent.putExtra("long",parking.getLongitude());

        intent.putExtra(Mappane.PARKING_DETAIL_ID_EXTRA, parking.mId);

        startActivity(intent);
    }
}

