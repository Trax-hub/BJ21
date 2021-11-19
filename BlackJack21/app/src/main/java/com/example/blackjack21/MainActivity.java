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
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.StackView;
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
    TextView balance, playerValue, dealerValue;
    ImageView dc1, dc2, dc3, dc4, dc5, dc6, pc1, pc2, pc3, pc4, pc5, pc6, stack;
    StackView playerView, dealerView;


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

        //TextView
        balance = findViewById(R.id.balance);
        playerValue = findViewById(R.id.playerValue);
        dealerValue = findViewById(R.id.dealerValue);

        //Bet (EditText)
        bet = findViewById(R.id.bet);

        //Stack cards
        stack = findViewById(R.id.stack);

        //Player & Dealer views
        playerView = findViewById(R.id.playerView);
        dealerView = findViewById(R.id.dealerView);


        balance.setText(Double.toString(game.getPlayer().getBalance()));

        betButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();

                game.getPlayer().setBet(Double.parseDouble(bet.getText().toString()));
                game.getPlayer().setBalance(game.getPlayer().getBalance() - game.getPlayer().getBet());
                game.distribute();
                balance.setText(Double.toString(game.getPlayer().getBalance()));

                try {
                    actualizeCards();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                enable(hitButton);
                enable(standButton);
                disable(splitButton);
                disable(doubleButton);
            }
        });

        hitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!game.getPlayer().getHand().isBusted() && !game.getPlayer().isStand()){
                    game.hit(game.getPlayer());
                    try {
                        actualizeCards();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(game.getPlayer().getHand().isBusted()){
                        disable(hitButton);
                        disable(standButton);
                        disable(splitButton);
                        disable(doubleButton);
                    }
                }
            }
        });

        standButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.stand(game.getPlayer());

                disable(hitButton);
                disable(splitButton);
                disable(doubleButton);
                disable(standButton);

                try {
                    actualizeCards();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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

                enable(hitButton);
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

    public void actualizeCards() throws InterruptedException {
        //playerView.set;
        playerValue.setText(Integer.toString(game.getPlayer().getHand().getValue()));
        dealerValue.setText(Integer.toString(game.getDealer().getHand().getValue()));
    }

    private void enable(AppCompatButton button) {
        button.setEnabled(true);
        button.setTextColor(getResources().getColor(R.color.white));
        button.setBackgroundResource(R.drawable.button_enabled);
    }

    private void disable(AppCompatButton button) {
        button.setEnabled(false);
        button.setTextColor(getResources().getColor(R.color.white_disabled));
        button.setBackgroundResource(R.drawable.button_disabled);
    }

    private void clearAll(){
        //Clear cards
        pc1.setImageResource(0);
        pc2.setImageResource(0);
        pc3.setImageResource(0);
        pc4.setImageResource(0);
        pc5.setImageResource(0);
        pc6.setImageResource(0);

        dc1.setImageResource(0);
        dc2.setImageResource(0);
        dc3.setImageResource(0);
        dc4.setImageResource(0);
        dc5.setImageResource(0);
        dc6.setImageResource(0);

        //clear Hands
        game.getPlayer().setHand(new Hand());
        game.getDealer().setHand(new Hand());
    }
}













