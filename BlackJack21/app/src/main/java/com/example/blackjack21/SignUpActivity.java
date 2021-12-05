package com.example.blackjack21;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private EditText pseudo, mail, password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        mail = findViewById(R.id.mail);
        password =findViewById(R.id.password);
        pseudo = findViewById(R.id.pseudo);
        Button button = findViewById(R.id.signupButton);

        button.setOnClickListener(view -> registerUser());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SignUpActivity.this, PortalActivity.class));
        finish();
    }

    private void registerUser(){
        String mailS = mail.getText().toString().trim();
        String passwordS = password.getText().toString().trim();
        String pseudoS = pseudo.getText().toString().trim();

        if(mailS.isEmpty()){
            mail.setError("Champ requis");
            mail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(mailS).matches()){
            mail.setError("Email non valide");
            mail.requestFocus();
            return;
        }

        if(passwordS.isEmpty()){
            password.setError("Champ requis");
            password.requestFocus();
            return;
        }

        if(passwordS.length() < 6){
            password.setError("Longueur minimum de 6 caractères");
            password.requestFocus();
            return;
        }

        if(pseudoS.isEmpty()){
            pseudo.setError("Champ requis");
            pseudo.requestFocus();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(mailS, passwordS)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        assert firebaseUser != null;
                        String uid = firebaseUser.getUid();
                        User user = new User(pseudoS, mailS, uid, 1000., 0L);

                        FirebaseDatabase.getInstance("https://black-jack-21-ede5c-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Users")
                                .child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this, "Sign up successfull", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                                    } else {
                                        Toast.makeText(SignUpActivity.this, "Sign up failed", Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                });
    }
}
