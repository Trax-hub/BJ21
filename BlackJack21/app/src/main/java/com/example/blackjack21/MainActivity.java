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
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    Game game = new Game();
    AppCompatButton hitButton, standButton, splitButton, doubleButton, betButton, halfBet, doubleBet;
    EditText bet;
    ImageView dc1, dc2, dc3, dc4, dc5, dc6, pc1, pc2, pc3, pc4, pc5, pc6;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Buttons
        hitButton = findViewById(R.id.hitButton);
        standButton = findViewById(R.id.standButton);
        splitButton = findViewById(R.id.splitButton);
        doubleButton = findViewById(R.id.doubleButton);
        betButton = findViewById(R.id.betButton);
        halfBet = findViewById(R.id.halfBet);
        doubleBet = findViewById(R.id.doubleBet);

        //Bet (EditText)
        bet = findViewById(R.id.bet);

        //Cards (ImageView)
        //Dealer's
        dc1 = findViewById(R.id.dc1);
        dc2 = findViewById(R.id.dc2);
        dc3 = findViewById(R.id.dc3);
        dc4 = findViewById(R.id.dc4);
        dc5 = findViewById(R.id.dc5);
        dc6 = findViewById(R.id.dc6);

        //Player's
        //TODO

        initializeCards();

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
                game.distribute();
                try {
                    actualizeCards();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        halfBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bet.setText(Integer.toString(Integer.parseInt(bet.getText().toString()) / 2));
            }
        });

        doubleBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bet.setText(Integer.toString(Integer.parseInt(bet.getText().toString()) * 2));
            }
        });
    }

    public void initializeCards(){
        dc1.setVisibility(View.INVISIBLE);
        dc2.setVisibility(View.INVISIBLE);
        dc3.setVisibility(View.INVISIBLE);
        dc4.setVisibility(View.INVISIBLE);
        dc5.setVisibility(View.INVISIBLE);
        dc6.setVisibility(View.INVISIBLE);
    }

    public void actualizeCards() throws InterruptedException {
        switch(game.getPlayer().getHand().getcardList().size()){
            default :
                System.out.println("There's a problem sir");
                break;
            case 2 :
                try
                {
                    display1();
                    Thread.sleep(1000);
                    display2();
                }
                catch(InterruptedException ex)
                {
                    Thread.currentThread().interrupt();
                }
                break;
            case 3 :
                Card c3 = (Card) game.getPlayer().getHand().getcardList().get(2);
                dc3.setImageResource(this.getResources().getIdentifier( c3.getRes(), "drawable", this.getPackageName()));
                dc3.setVisibility(View.VISIBLE);
                break;
            case 4 :
                Card c4 = (Card) game.getPlayer().getHand().getcardList().get(2);
                dc4.setImageResource(this.getResources().getIdentifier( c4.getRes(), "drawable", this.getPackageName()));
                dc4.setVisibility(View.VISIBLE);
                break;
            case 5 :
                Card c5 = (Card) game.getPlayer().getHand().getcardList().get(2);
                dc5.setImageResource(this.getResources().getIdentifier( c5.getRes(), "drawable", this.getPackageName()));
                dc5.setVisibility(View.VISIBLE);
                break;
            case 6:
                Card c6 = (Card) game.getPlayer().getHand().getcardList().get(2);
                dc6.setImageResource(this.getResources().getIdentifier( c6.getRes(), "drawable", this.getPackageName()));
                dc6.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void display1(){
        Card c1 = (Card) game.getPlayer().getHand().getcardList().get(0);
        dc1.setImageResource(this.getResources().getIdentifier( c1.getRes(), "drawable", this.getPackageName()));
        dc1.setVisibility(View.VISIBLE);
    }

    public void display2(){
        Card c2 = (Card) game.getPlayer().getHand().getcardList().get(1);
        dc1.setImageResource(this.getResources().getIdentifier( c2.getRes(), "drawable", this.getPackageName()));
        dc2.setVisibility(View.VISIBLE);
    }

}













