package com.example.blackjack21;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MenuActivity extends AppCompatActivity {

    ImageView real, training, rules, logout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        real = findViewById(R.id.real);
        training = findViewById(R.id.training);
        rules = findViewById(R.id.rules);
        logout = findViewById(R.id.logout);

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(this, PortalActivity.class));
        }

        real.setOnClickListener(v -> startActivity(new Intent(this, RealGameActivity.class)));
        training.setOnClickListener(v -> startActivity(new Intent(this, TrainingModeActivity.class)));
        rules.setOnClickListener(v -> startActivity(new Intent(this, RulesActivity.class)));
        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MenuActivity.this, PortalActivity.class));
            finish();
        });
    }
}
