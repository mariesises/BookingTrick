package com.example.bookingtrick.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingtrick.R;
import com.example.bookingtrick.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements TextWatcher {

    private FirebaseAuth auth;
    EditText name, email, password, password2;
    Button registerUser, returnLogin;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        name = findViewById(R.id.registerName);
        email = findViewById(R.id.registerEmail);
        password = findViewById(R.id.registerPassword);
        password.addTextChangedListener(this);

        password2 = findViewById(R.id.registerPassword2);

        name.setTextColor(Color.WHITE);
        email.setTextColor(Color.WHITE);
        password.setTextColor(Color.WHITE);
        password2.setTextColor(Color.WHITE);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");
        auth = FirebaseAuth.getInstance();

        registerUser = findViewById(R.id.registeruser);
        returnLogin = findViewById(R.id.returnlogin);

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        returnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent register = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(register);
            }
        });
    }

    @Override
    public void beforeTextChanged(
            CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        updatePasswordStrengthView(s.toString());
    }

    //Se utiliza para validar los datos principales dados por el usuario

    private void register() {
        String namee = this.name.getText().toString();
        String email = this.email.getText().toString();
        String pass1 = this.password.getText().toString();
        String pass2 = this.password2.getText().toString();
        if (email.isEmpty() || pass1.isEmpty() || pass2.isEmpty() || namee.isEmpty()) {
            Toast.makeText(this, "Algún Campo Vacío", Toast.LENGTH_SHORT).show();
        } else if (!validarEmail(email)) {
            Toast.makeText(this, "Email en Formato Incorrecto", Toast.LENGTH_SHORT).show();
        } else if (pass1.length() < 6) {
            Toast.makeText(this, "La contraseña tiene que tener al menos 6 Caracteres", Toast.LENGTH_SHORT).show();
        } else if ((!pass1.equals(pass2))) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        } else {
            checkUsuario(namee, email, pass1);
            //comprueba que no haya ninguno existente
        }
    }

    /*
        Sirve para comprobar la robustez de la contraseña dada, esta sacado de
        https://github.com/yesterselga/password-strength-checker-android
     */
    private void updatePasswordStrengthView(String password) {

        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView strengthView = findViewById(R.id.password_strength);
        if (TextView.VISIBLE != strengthView.getVisibility())
            return;

        if (password.isEmpty()) {
            strengthView.setText("");
            progressBar.setProgress(0);
            return;
        }

        PasswordStrength str = PasswordStrength.calculateStrength(password);
        strengthView.setText(str.getText(this));
        strengthView.setTextColor(str.getColor());

        progressBar.getProgressDrawable().setColorFilter(str.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);
        if (str.getText(this).equals("Weak")) {
            progressBar.setProgress(25);
        } else if (str.getText(this).equals("Medium")) {
            progressBar.setProgress(50);
        } else if (str.getText(this).equals("Strong")) {
            progressBar.setProgress(75);
        } else {
            progressBar.setProgress(100);
        }
    }

    /*
        Se encarga de comprobar si el name dado ya está utilizado, para ello se obtendrá la Query de todos los names
        que hay en la BD y luego comprobará si la collection devuelta tiene algún elemento (snapshot.getChildrenCount() > 0)
        si es así entonces ese name ya está utilizado
    */
    private void checkUsuario(String name, String email, String passw) {

        FirebaseDatabase.getInstance().getReference("Names/" + name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(RegisterActivity.this, "Nombre de Usuario ya Utilizado", Toast.LENGTH_SHORT).show();
                } else {
                    registrarUsuario(name, email, passw);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    /*
        Se utiliza para registrar al usuario, dentro se comprobará si el email dado ya está utilizado y se enviará
        un email de verificación para proceder al login
    */
    private void registrarUsuario(String name, String email, String passw) {

        auth.createUserWithEmailAndPassword(email, passw)
                .addOnCompleteListener(this, task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(this, "Email ya Utilizado", Toast.LENGTH_SHORT).show();
                    } else {
                        FirebaseUser user = auth.getCurrentUser();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users/" + user.getUid());
                        User newUser = new User(name,user.getUid(),email  ,"");
                        databaseReference.push().setValue(newUser);
                        FirebaseDatabase.getInstance().getReference("Names/" + name).setValue("");

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name).build();
                        Objects.requireNonNull(user).updateProfile(profileUpdates);
                        Objects.requireNonNull(user).sendEmailVerification();

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        Toast.makeText(this, "Verifica tu dirección de correo", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
}