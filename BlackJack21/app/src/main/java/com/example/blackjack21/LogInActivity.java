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

public class LogInActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.mail);
        editTextPassword = findViewById(R.id.password);
        Button signIn = findViewById(R.id.loginButton);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        signIn.setOnClickListener(view -> userLogin());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(LogInActivity.this, PortalActivity.class));
        finish();
    }

    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Not a valid item");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Min length is 6");
            editTextPassword.requestFocus();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                startActivity(new Intent(LogInActivity.this, MenuActivity.class));
                finish();
            } else {
                Toast.makeText(LogInActivity.this, "Failed to login", Toast.LENGTH_LONG).show();
            }
        });

    }
}
