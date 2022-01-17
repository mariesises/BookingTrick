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

import com.example.bookingtrick.R;
import com.example.bookingtrick.login.MainActivity;
import com.example.bookingtrick.model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth auth;
    private User user;
    private de.hdodenhof.circleimageview.CircleImageView imagenPerfil;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        user = Session.getUser();
        auth = FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        Menu menu = navigationView.getMenu();

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, myToolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"NonConstantResourceId", "NewApi"})
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {


            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FragmentController.getCenterFragment()).commit();
                break;

            case R.id.nav_gallery:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FragmentController.getMyBookingsFragment()).commit();
                break;


            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, FragmentController.getSettingsFragment()).commit();
                break;


            case R.id.nav_exit:
                exit();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint({"ResourceAsColor", "ResourceType"})
    private void exit() {
        AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(this, Color.GRAY);
        builder.setTitle(R.string.sureExit);
        builder.setCancelable(false);

        builder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
            Session.deleteSharedPreferences();
            dialogInterface.dismiss();
            auth.signOut();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        builder.setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.dismiss());

        builder.show();
    }


    private void setPhoto() {
        imagenPerfil.setImageResource(R.drawable.usuario);
    }
}