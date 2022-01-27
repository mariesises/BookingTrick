package com.example.bookingtrick.main;

import android.annotation.SuppressLint;

import com.example.bookingtrick.fragments.center.CenterFragment;
import com.example.bookingtrick.fragments.booking.MyBookingsFragment;
import com.example.bookingtrick.fragments.SettingsFragment;

/**
 * Controlador para el menu, donde indica adonde mandarte dependiendo de la seleccion en el menu
 */
@SuppressLint("StaticFieldLeak")
public class FragmentController {
    private static MyBookingsFragment myBookingsFragment;
    private static SettingsFragment settingsFragment;
    private static CenterFragment centerFragment;


    public FragmentController() {
    }

    //Seleccion de la lista de mis reservas
    public static MyBookingsFragment getMyBookingsFragment() {
        if (myBookingsFragment == null) {
            myBookingsFragment = new MyBookingsFragment();
        }
        return myBookingsFragment;
    }

    //Seleccion de los ajustes del usuario
    public static SettingsFragment getSettingsFragment() {
        if (settingsFragment == null) {
            settingsFragment = new SettingsFragment();
        }
        return settingsFragment;
    }

    //Seleccion del menú principal
    public static CenterFragment getCenterFragment() {
        if (centerFragment == null) {
            centerFragment = new CenterFragment();
        }
        return centerFragment;
    }
}
