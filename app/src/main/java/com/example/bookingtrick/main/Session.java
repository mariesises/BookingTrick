package com.example.bookingtrick.main;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;

import com.example.bookingtrick.model.Center;
import com.example.bookingtrick.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;


@SuppressLint("StaticFieldLeak")
public class Session {
    private static SharedPreferences sharedPreferences;
    private static User user;
    private static ArrayList<Center> centerList;

    public Session() {
    }

    public static void setSharedPreferences(SharedPreferences sharedPreferences) {
        Session.sharedPreferences = sharedPreferences;
    }

    public static SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public static User getUser() {
        if (user == null) {
            Gson gson = new Gson();
            String json = sharedPreferences.getString("User", null);
            return gson.fromJson(json, User.class);
        }
        return user;
    }

    public static void deleteSharedPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        user = null;
        editor.clear().apply();
    }


    public static void setCenters(ArrayList<Center> centerList) {
        Session.centerList = centerList;
    }

    public static ArrayList<Center> getCenterList() {
        return centerList;
    }

    public static void addCenterToList(Center center) {
        centerList.add(center);
    }

    public static void removeCenterFromList(Center center) {
        centerList.remove(center);
    }

    public static void clearGymList() {
        centerList.clear();
    }

    public static void setUser(User value) {
        Session.user = value;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(value);
        editor.putString("User", json).apply();
    }
}



