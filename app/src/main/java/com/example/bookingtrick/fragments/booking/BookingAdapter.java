package com.example.bookingtrick.fragments.booking;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.bookingtrick.R;
import com.example.bookingtrick.fragments.reserve.MakeReserveActivity;
import com.example.bookingtrick.main.Session;
import com.example.bookingtrick.model.Booking;
import com.example.bookingtrick.model.User;

import java.util.ArrayList;
import java.util.List;

public class BookingAdapter extends ArrayAdapter<Booking> {

    private final Context context;
    private List<Booking> bookingList;
    private MakeReserveActivity bookingActivity;
    private final MyBookingsFragment fragment;
    private User currentUser;


    /**
     * Adaptador de la lista de reservas
     *
     * @param context
     * @param booking
     * @param fragment
     */
    public BookingAdapter(Context context, List<Booking> booking, MyBookingsFragment fragment) {
        super(context, R.layout.booking_frame, booking);
        this.context = context;
        this.bookingList = booking;
        this.fragment = fragment;
    }

    /**
     * La vista que crea con los datos de la lista
     *
     * @param position
     * @param view
     * @param parent
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //Infla la vista creada para mostrar los datos de la reserva
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.booking_frame, parent, false);
        }

        //Declara las variables del layout
        TextView textViewCenter = view.findViewById(R.id.centerNamer);
        TextView textViewDate = view.findViewById(R.id.centerDate);
        TextView textViewHour = view.findViewById(R.id.centerHour);


        Booking b = bookingList.get(position);

        //Muestra el nombre del centro, la hora y la fecha de la reserva
        textViewCenter.setText(b.getCenter());
        textViewDate.setText(b.getDate());
        textViewHour.setText(b.getHour());

        return view;
    }

    public void setList(ArrayList<Booking> list) {
        bookingList = list;
    }
}
