package com.example.bookingtrick.fragments;


import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.example.bookingtrick.R;
import com.example.bookingtrick.main.MenuActivity;

public class SettingsFragment extends PreferenceFragmentCompat{

    private MenuActivity homeActivity;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
