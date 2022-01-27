package com.example.bookingtrick.fragments;


import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.bookingtrick.R;
import com.example.bookingtrick.main.MenuActivity;
import com.example.bookingtrick.main.Session;
import com.example.bookingtrick.model.User;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SettingsFragment extends PreferenceFragmentCompat {

    /**
     * Archivo de preferencias para cambiar el nombre del usuario mediante una pestaña de ajustes
     * @param savedInstanceState
     * @param rootKey
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        //Declarar la sesion del usuario y seleccionando el name, cambiarlo en la base de datos
        User user = Session.getUser();

        findPreference("email").setTitle(user.getEmail());
        Preference Name = findPreference("name");
        Name.setTitle(user.getName());

        Name.setOnPreferenceChangeListener((preference, newValue) -> {
            String newName = newValue.toString();

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("name",newName);
            FirebaseDatabase.getInstance().getReference("Users/"+user.getUid()+"/"+user.getObjectID()).updateChildren(hashMap);
            user.setName(newName);
            Name.setTitle(newName);
            return false;
        });
    }
}
