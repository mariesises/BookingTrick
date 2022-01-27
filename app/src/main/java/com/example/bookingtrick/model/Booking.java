package com.example.bookingtrick.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.Date;
import java.util.List;

public class Booking implements Parcelable {

    private String center;
    private String date;
    private String hour;
    private List<Booking> bookinglist;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Booking(String center, String date, String hour) {
        this.center = center;
        this.date = date;
        this.hour = hour;
    }

    public Booking(){}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Booking(Parcel parcel) {
        this.center = parcel.readString();
        this.date = parcel.readString();
        this.hour = parcel.readString();
    }

    public static final Parcelable.Creator<Booking> CREATOR = new Parcelable.Creator<Booking>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void readFromParcel(Parcel in) {
        this.center = in.readString();
        this.date = in.readString();
        this.hour = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.center);
        parcel.writeString(this.date);
        parcel.writeString(this.hour);
    }

    public void removeBooking(Booking booking) {
        bookinglist.remove(booking);
    }

    public void addBookingList(Booking booking) {
        if (!bookinglist.contains(booking)) {
            bookinglist.add(booking);
        }
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
}
