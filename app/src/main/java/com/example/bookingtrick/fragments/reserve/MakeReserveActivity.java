package com.example.bookingtrick.fragments.reserve;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingtrick.R;
import com.example.bookingtrick.fragments.reserve.SelectHoursActivity;

public class MakeReserveActivity extends AppCompatActivity {
    //Declaracion de variables
    CalendarView cal;
    TextView myDate;
    Button save;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_reserve);

        //Asignar variables
        cal = findViewById(R.id.calendarView);
        myDate = findViewById(R.id.textView);
        save = findViewById(R.id.button);


        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                //MOstrar la fecha en el textview al seleccionar una
                String date = i2 + "/" + (i1 + 1) + "/" + i;
                myDate.setText(date);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selecthours = new Intent(getApplicationContext(), SelectHoursActivity.class);
                startActivity(selecthours);
            }
        });

    }
}
