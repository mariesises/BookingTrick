package com.example.bookingtrick.fragments.booking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bookingtrick.R;
import com.example.bookingtrick.main.FragmentController;
import com.example.bookingtrick.main.Session;
import com.example.bookingtrick.model.Booking;
import com.example.bookingtrick.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyBookingsFragment extends Fragment {
    //Declaracion de variables
    private ListView listView;
    private User user;
    private ArrayList<Booking> mybookings;

    public MyBookingsFragment() {
    }

    /**
     * Mostrará la lista de reservas realizadas
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        mybookings = Session.getBookingList();

        //Declara la vista que se va a inflar, la lista donde se añaden los datos y el adaptador de donde saca el formato de la lista
        View view = inflater.inflate(R.layout.fragment_my_bookings, null);
        listView = view.findViewById(R.id.myBookingsList);

        BookingAdapter bookingAdapter = new BookingAdapter(getActivity(),mybookings,this);
        listView.setAdapter(bookingAdapter);

        return view;
    }
}
