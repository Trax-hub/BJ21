package com.example.blackjack21;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Game game = new Game();
    AppCompatButton hitButton;
    AppCompatButton standButton;
    AppCompatButton splitButton;
    AppCompatButton doubleButton;
    AppCompatButton betButton;
    EditText bet;
    ListView dealerList;
    ListView playerList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hitButton = findViewById(R.id.hitButton);
        standButton = findViewById(R.id.standButton);
        splitButton = findViewById(R.id.splitButton);
        doubleButton = findViewById(R.id.doubleButton);
        betButton = findViewById(R.id.betButton);
        bet = findViewById(R.id.bet);
        dealerList = findViewById(R.id.dealerList);
        playerList = findViewById(R.id.playerList);

        hitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.hit(game.getPlayer());
            }
        });

        standButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.stand(game.getPlayer());
            }
        });

        splitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.split(game.getPlayer());
            }
        });

        doubleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.dobble(game.getPlayer());
            }
        });

        betButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.getPlayer().setBet(Double.parseDouble(bet.getText().toString()));
            }
        });
        game.distribute();
        display((ArrayList<Card>) game.getDealer().getHand().getcardList());
        display((ArrayList<Card>) game.getPlayer().getHand().getcardList());
    }


    private void display(ArrayList<Card> cards){
        CardAdapter cardAdapter = new CardAdapter(this, cards);
        playerList.setAdapter(cardAdapter);
    }

}