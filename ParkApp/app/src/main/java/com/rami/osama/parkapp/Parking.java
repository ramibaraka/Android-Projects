package com.rami.osama.parkapp;

import android.os.Parcel;
import android.os.Parcelable;


public final class Parking implements Parcelable
{
    public final double mLatitude, mLongitude;
    public final String mAddress, mId;
    public final int mParkingSpots, mDistance;

    public Parking(String address, int parkingSpots, double latitude, double longitude, int distance, String id)
    {
        mAddress = address;
        mParkingSpots = parkingSpots;
        mLatitude = latitude;
        mLongitude = longitude;
        mDistance = distance;
        mId = id;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAddress);
        dest.writeString(mId);
        dest.writeInt(mParkingSpots);
        dest.writeInt(mDistance);
        dest.writeDouble(mLatitude);
        dest.writeDouble(mDistance);
    }

    private Parking(Parcel in){
        this.mAddress = in.readString();
        this.mId = in.readString();
        this.mParkingSpots = in.readInt();
        this.mDistance = in.readInt();
        this.mLatitude = in.readDouble();
        this.mLongitude = in.readDouble();

    }

    public static final Parcelable.Creator<Parking> CREATOR = new Parcelable.Creator<Parking>() {

        @Override
        public Parking createFromParcel(Parcel source) {
            return new Parking(source);
        }

        @Override
        public Parking[] newArray(int size) {
            return new Parking[size];
        }
    };
}