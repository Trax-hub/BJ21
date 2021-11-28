package com.example.blackjack21;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class PortalActivity extends AppCompatActivity {
    private AppCompatButton login, signup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);

        login = findViewById(R.id.loginButton);
        signup = findViewById(R.id.signupButton);

        login.setOnClickListener(v -> {
            startActivity(new Intent(this, LogInActivity.class));
        });

        signup.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });

    }


}
