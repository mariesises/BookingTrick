package com.example.bookingtrick.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookingtrick.R;
import com.example.bookingtrick.fragments.center.CenterFragment;
import com.example.bookingtrick.main.Session;
import com.example.bookingtrick.main.MenuActivity;
import com.example.bookingtrick.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText email, pass;
    Button buttonRegister, buttonLogin;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Las preferencias para mantener la sesion iniciada en al inciar la aplicacion de nuevo en caso de que no se haya cerrado
        SharedPreferences sharedPreferences = getSharedPreferences("Session", MODE_PRIVATE);
        Session.setSharedPreferences(sharedPreferences);

        if (sharedPreferences.getString("User", null) != null) {
            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
            finish();
        }

        email = findViewById(R.id.loginEmail);
        pass = findViewById(R.id.loginPassword);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);

        auth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(view -> login());

        buttonRegister.setOnClickListener(
                view -> {
                    Intent i = new Intent(this, RegisterActivity.class);
                    startActivity(i);
                    finish();
                }
        );
    }

    /**
     * Metodo para loguearte mediante el email y la contraseña que ya estan registrados en la base de datos y si el correo esta verificado
     * Analiza si la sesion esta iniciada y que debe hacer en cada caso
     */

    private void login() {
        String email = this.email.getText().toString();
        String pass = this.pass.getText().toString();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, R.string.login1,
                    Toast.LENGTH_SHORT).show();
        } else if (!validarEmail(email)) {
            Toast.makeText(this, R.string.login2,
                    Toast.LENGTH_SHORT).show();
        } else {
            auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, task -> {
                        if (!task.isSuccessful()) {
                            Toast.makeText(this, R.string.login3,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseUser user = Objects.requireNonNull(task.getResult()).getUser();
                            if (Objects.requireNonNull(user).isEmailVerified()) {
                                reference = FirebaseDatabase.getInstance().getReference().child("Users/" + user.getUid());
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Intent i = new Intent(getApplicationContext(), MenuActivity.class);
                                        User u = snapshot.getChildren().iterator().next().getValue(User.class);
                                        if (u.getObjectID().isEmpty()) {
                                            u.setObjectID(snapshot.getKey());
                                            FirebaseDatabase.getInstance().getReference().child("Users/" + user.getUid() + "/" + snapshot.getChildren().iterator().next().getKey())
                                                    .child("objectID").setValue(snapshot.getChildren().iterator().next().getKey());
                                        }
                                        Session.setUser(u);
                                        startActivity(i);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            } else {
                                Toast.makeText(this, R.string.login4,
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

}



