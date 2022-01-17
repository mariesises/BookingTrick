package com.example.bookingtrick.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Center implements Parcelable {

    private List<Booking> bookinglist;
    private String name;
    private String street;
    private String hours;
    private String date;
    private Double latitude;
    private Double longitud;
    private String image;


    public Center(String name, String street, String hours, String date, Double latitude, Double longitud, String image) {
        this.name = name;
        this.street = street;
        this.hours = hours;
        this.date = date;
        this.latitude = latitude;
        this.longitud = longitud;
        this.image = image;
    }


    protected Center(Parcel in) {
        name = in.readString();
        street = in.readString();
        hours = in.readString();
        date = in.readString();
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitud = null;
        } else {
            longitud = in.readDouble();
        }
        image = in.readString();
    }

    public static final Creator<Center> CREATOR = new Creator<Center>() {
        @Override
        public Center createFromParcel(Parcel in) {
            return new Center(in);
        }

        @Override
        public Center[] newArray(int size) {
            return new Center[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHoras() {
        return hours;
    }

    public void setHoras(String horas) {
        this.hours = horas;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void removeBooking(Booking booking) {
        bookinglist.remove(booking);
    }

    public void addBookingList(Booking booking){
        if(!bookinglist.contains(booking)){
            bookinglist.add(booking);
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.name);
        parcel.writeString(this.street);
    }
}
