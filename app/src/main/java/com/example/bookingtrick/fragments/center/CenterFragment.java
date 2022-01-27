package com.example.bookingtrick.fragments.center;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bookingtrick.R;
import com.example.bookingtrick.main.Session;
import com.example.bookingtrick.model.Center;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CenterFragment extends Fragment {

    private ArrayList<Center> centerlist;
    private ListView listView;

    public CenterFragment() {
    }

    /**
     * Adaptador que infla la lista y muestra los centros
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        centerlist = Session.getCenterList();

        //Declara la vista que se va a inflar, la lista donde se añaden los datos y el adaptador de donde saca el formato de la lista
        View view = inflater.inflate(R.layout.fragment_home, null);
        listView = view.findViewById(R.id.homeLayout);


        CenterAdapter centerAdapter = new CenterAdapter(getActivity(),centerlist,this);
        listView.setAdapter(centerAdapter);

        return view;
    }
}

