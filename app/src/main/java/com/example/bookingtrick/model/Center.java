package com.example.bookingtrick.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.List;

public class Center implements Parcelable {
    private String name;
    private String street;
    private String date;
    private Double latitude;
    private Double longitud;
    private String image;


    public Center(){}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Center(String name, String street, String date, Double latitude, Double longitud, String image) {
        this.name = name;
        this.street = street;
        this.date = date;
        this.latitude = latitude;
        this.longitud = longitud;
        this.image = image;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Center(Parcel parcel) {
        this.name = parcel.readString();
        this.street = parcel.readString();
    }

    public static final Parcelable.Creator<Center> CREATOR = new Parcelable.Creator<Center>() {
        @RequiresApi(api = Build.VERSION_CODES.O)
        public Center createFromParcel(Parcel in) {
            return new Center(in);
        }
        public Center[] newArray(int size) {
            return new Center[size];
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void readFromParcel(Parcel in) {
        this.name = in.readString();
        this.street = in.readString();
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


}
