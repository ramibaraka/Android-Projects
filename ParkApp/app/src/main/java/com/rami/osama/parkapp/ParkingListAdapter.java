package com.rami.osama.parkapp;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public final class ParkingListAdapter extends ArrayAdapter<Parking>
{
    public ParkingListAdapter(Context context, List<Parking> parkings)
    {
        super(context, 0, parkings);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_parking_list_item, null);
        }

        TextView nameText = (TextView) convertView.findViewById(R.id.parking_item_name);
        TextView distanceText = (TextView) convertView.findViewById(R.id.parking_item_distance);
        TextView spotsText = (TextView) convertView.findViewById(R.id.parking_item_spots);

        Parking parking = getItem(position);

        nameText.setText(parking.mAddress);
        Resources rs = getContext().getResources();
        distanceText.setText(rs.getString(R.string.distance) + " " + parking.mDistance + "m");

        Integer parkingSpots = parking.mParkingSpots;
        String parkSpots = Integer.toString(parkingSpots);
        spotsText.setText("(" + parkSpots + ")");

        return convertView;
    }
}
