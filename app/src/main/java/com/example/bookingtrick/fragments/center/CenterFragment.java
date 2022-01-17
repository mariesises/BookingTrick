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

import java.util.ArrayList;

public class CenterFragment extends Fragment {

    private ArrayList<Center> centerlist;
    private ListView listView;

    public CenterFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        centerlist = Session.getCenterList();

        View view = inflater.inflate(R.layout.activity_center_selection, null);
        listView = view.findViewById(R.id.listCenter);

        /*
        CenterAdapter centerAdapter = new CenterAdapter(getActivity(), centerlist, this);
        listView.setAdapter(centerAdapter);

*/

        return view;
    }
}
