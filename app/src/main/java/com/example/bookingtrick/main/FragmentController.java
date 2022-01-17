package com.example.bookingtrick.main;

import android.annotation.SuppressLint;

import com.example.bookingtrick.fragments.center.CenterFragment;
import com.example.bookingtrick.fragments.MyBookingsFragment;
import com.example.bookingtrick.fragments.SettingsFragment;

@SuppressLint("StaticFieldLeak")
public class FragmentController {
    private static MyBookingsFragment myBookingsFragment;
    private static SettingsFragment settingsFragment;
    private static CenterFragment centerFragment;


    public FragmentController(){}

    public static MyBookingsFragment getMyBookingsFragment(){
        if (myBookingsFragment == null){
            myBookingsFragment = new MyBookingsFragment();
        }
        return myBookingsFragment;
    }

    public static SettingsFragment getSettingsFragment(){
        if (settingsFragment == null){
            settingsFragment = new SettingsFragment();
        }
        return settingsFragment;
    }

    public static CenterFragment getCenterFragment(){
        if (centerFragment == null){
            centerFragment = new CenterFragment();
        }
        return centerFragment;
    }
}
