package com.example.bookingtrick.fragments.reserve;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.bookingtrick.R;

import java.util.ArrayList;
import java.util.List;

public class SelectHoursActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hours);

        listView = findViewById(R.id.listView);

        hours = new ArrayList<String>();
        hours.add("9:00-10:00");
        hours.add("10:00-11:00");
        hours.add("11:00 -12:00");
        hours.add("17:00-18:00");
        hours.add("18:00-19:00");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, hours);
        listView.setAdapter(adapter);

    }
}