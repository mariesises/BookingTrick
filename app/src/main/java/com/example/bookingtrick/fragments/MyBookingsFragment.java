package com.example.bookingtrick.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bookingtrick.R;
import com.example.bookingtrick.model.Booking;
import com.example.bookingtrick.model.User;

import java.util.ArrayList;

public class MyBookingsFragment extends Fragment {
    private ListView listView;
    private User user;
    private ArrayList<Booking> mybookings;

    public MyBookingsFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bookings,container,false);
        listView = view.findViewById(R.id.myBookingsList);




        return view;
    }
}
