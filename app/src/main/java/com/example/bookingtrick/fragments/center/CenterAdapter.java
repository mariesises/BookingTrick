package com.example.bookingtrick.fragments.center;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;

import com.example.bookingtrick.R;
import com.example.bookingtrick.fragments.reserve.MakeReserveActivity;
import com.example.bookingtrick.model.Center;

import java.util.ArrayList;
import java.util.List;

public class CenterAdapter extends ArrayAdapter<Center> {
    //Declaracion de variables
    private List<Center> centerList;
    private final CenterFragment fragment;
    private final Context context;

    /**
     * Adapatador para la lista
     * @param context
     * @param center
     * @param fragment
     */
    public CenterAdapter(Context context, List<Center> center, CenterFragment fragment) {
        super(context, R.layout.center_frame, center);
        this.context = context;
        this.centerList = center;
        this.fragment = fragment;
    }

    /**
     * Que vista debe mostrar, en este caso la lista de centros deportivos
     * @param position
     * @param view
     * @param parent
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //Infla la vista creada para mostrar los centros deportivos
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.center_frame, parent, false);
        }
        //Variables
        TextView textViewName = view.findViewById(R.id.itemName);
        textViewName.setHeight(60);
        TextView textViewStreet = view.findViewById(R.id.itemStreet);
        textViewStreet.setHeight(60);
        TextView bookingsButton = view.findViewById(R.id.showBookingsButton);
        ImageView image = view.findViewById(R.id.itemImage);


        Center c = centerList.get(position);

        //Muestra el nombre y la calle del centro
        textViewName.setText(c.getName());
        textViewStreet.setText(c.getStreet());

        //Para cargar la imagen dentro del image view y mediante CENTER_CROP escale la imagen dentro del marco
        if (!c.getImage().isEmpty()) {
            Glide.with(context).load(Uri.parse(c.getImage())).into(image);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        bookingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReserve = new Intent(getContext(), MakeReserveActivity.class);
                getContext().startActivity(intentReserve);
            }
        });

        return view;
    }

    public void setCenterList(ArrayList<Center> list) {
        centerList = list;
    }
}
