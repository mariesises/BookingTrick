package com.example.bookingtrick.fragments.center;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CenterFragment extends Fragment {

    private ArrayList<Center> centerlist;
    private ListView listView;
    private DatabaseReference myRef;

    public CenterFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        centerlist = Session.getCenterList();

        View view = inflater.inflate(R.layout.fragment_home, null);
        listView = view.findViewById(R.id.homeLayout);


        ArrayList<Integer> integers = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        CenterAdapter centerAdapter = new CenterAdapter(getActivity(), centerlist, this);
        int max = 0;
        for (Integer i : integers) {
            if (i == max) {
                FirebaseDatabase.getInstance().getReference("Center/" + name.get(integers.indexOf(i)))
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (dataSnapshot.exists()) {
                                    String name = dataSnapshot.child("name").getValue(String.class);
                                    String street = dataSnapshot.child("street").getValue(String.class);
                                    listView.setAdapter(centerAdapter);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("Fallo la lectura: " + databaseError.getCode());
                            }
                        });
            }

        }

        return view;
    }
}

