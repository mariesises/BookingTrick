package com.example.bookingtrick.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.bookingtrick.R;
import com.example.bookingtrick.login.MainActivity;
import com.example.bookingtrick.model.Booking;
import com.example.bookingtrick.model.Center;
import com.example.bookingtrick.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth auth;
    private User user;
    private de.hdodenhof.circleimageview.CircleImageView imagenPerfil;
    private DrawerLayout drawerLayout;
    private ArrayList<Center> centerList;
    private ArrayList<Booking> mybookings;

    /**
     * Metodo on create de la vista principal
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //variables de autentificacion y sesion
        user = Session.getUser();
        auth = FirebaseAuth.getInstance();

        //Tipo de vista que se dibuja en el menu
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        //Tipo de menu
        Menu menu = navigationView.getMenu();

        //Barra de acciones de arriba
        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        //Que hace la barra cuando abre o se cierra/colapsa
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, myToolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //adonde manda si no hay nada seleccionado de primeras y que selecciona en el menu
        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            getCentersFromDataBase();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    /**
     * En el menu lateral, va a salir la informacion del usuario, correo electronico y Usuario
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        TextView emailHeader = findViewById(R.id.emailHeader);
        TextView nameHeader = findViewById(R.id.nameHeader);

        emailHeader.setText(user.getEmail());
        nameHeader.setText(user.getName());

        imagenPerfil = findViewById(R.id.imagenPerfil);
        setPhoto();
        return true;

    }

    /**
     * El menu de navegacion de la aplicacion
     * Sirve para decir a que pantalla dirije dependiendo de la opcion que se pulse en el menú desplegable
     * @param item
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"NonConstantResourceId", "NewApi"})
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            //Pantalla principal para reservar en el centro deportivo
            case R.id.nav_home:
                getCentersFromDataBase();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FragmentController.getCenterFragment()).commit();
                break;
            //Pantalla de las reservas realizadas
            case R.id.nav_gallery:
                getBookingsFromDataBase();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FragmentController.getMyBookingsFragment()).commit();
                break;

            //Pantalla para los ajustes de la aplicacion
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FragmentController.getSettingsFragment()).commit();
                break;

            //Pantalla para salir de la sesion
            case R.id.nav_exit:
                exit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Que hace cuando se cierra o abre el menu
     */
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Metodo para salir de la sesion mediante una alerta
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"ResourceAsColor", "ResourceType"})
    private void exit() {
        AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(this, Color.GRAY);
        builder.setTitle(R.string.sureExit);
        builder.setCancelable(false);

        //Borra de las sharedpreferences la sesion y sale al menu de logueo
        builder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
            Session.deleteSharedPreferences();
            dialogInterface.dismiss();
            auth.signOut();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        //Si decide no salir de la aplicacion cancela la alerta
        builder.setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.dismiss());

        builder.show();
    }

    //metodo para mostrar una imagen de perfil como usuario al desplegar el menu
    private void setPhoto() {
        imagenPerfil.setImageResource(R.drawable.usuario);
    }

    //Metodo que recoge los datos de los centros deportivos de la base de datos en Firebase
    private void getCentersFromDataBase() {

        centerList = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Center").addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    System.out.println(ds.getValue());
                    Center newCenter = ds.getValue(Center.class);
                    centerList.add(newCenter);
                }

                Fragment homeFragment = FragmentController.getCenterFragment();
                Session.setCenters(centerList);

                if (!getSupportFragmentManager().isDestroyed()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //Metodo que recoge los datos de las reservas realizadas de la base de datos de Firebase
    private void getBookingsFromDataBase() {

        mybookings = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference().child("Users").child("/booking").addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    System.out.println(ds.getValue());
                    Booking newBooking = ds.getValue(Booking.class);
                    mybookings.add(newBooking);
                }

                Fragment bookingFragment = FragmentController.getMyBookingsFragment();
                Session.setBookings(mybookings);

                if (!getSupportFragmentManager().isDestroyed()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bookingFragment).commit();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}