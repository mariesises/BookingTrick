package com.example.bookingtrick.fragments.center;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.bookingtrick.R;
import com.example.bookingtrick.model.Center;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CenterAdapter extends ArrayAdapter<Center> {

    private List<Center> centerList;
    private final CenterFragment fragment;
    private final Context context;
    private DatabaseReference myRef;


    public CenterAdapter(Context context, List<Center> center, CenterFragment fragment) {
        super(context, R.layout.center_frame, center);
        this.context = context;
        this.centerList = center;
        this.fragment = fragment;
    }

    public View getView(int position, View view, ViewGroup parent) {


        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.center_frame, parent, false);
        }
        TextView textViewName = view.findViewById(R.id.itemName);
        textViewName.setHeight(60);
        TextView textViewStreet = view.findViewById(R.id.itemStreet);
        textViewStreet.setHeight(60);
        TextView bookingsButton = view.findViewById(R.id.showBookingsButton);
        ImageView image = view.findViewById(R.id.itemImage);


        Center c = centerList.get(position);
        //myRef = FirebaseDatabase.getInstance().getReference("Center/" + c.getName());

        textViewName.setText(c.getName());
        textViewStreet.setText(c.getStreet());

        if (!c.getImage().isEmpty()) {
            Glide.with(context).load(Uri.parse(c.getImage())).into(image);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        return view;
    }

    public void setCenterList(ArrayList<Center> list) {
        centerList = list;
    }
}
