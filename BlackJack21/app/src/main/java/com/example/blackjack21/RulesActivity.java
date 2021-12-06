package com.example.blackjack21;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        TextView rules = findViewById(R.id.rules);

        rules.setText(
                "The BlackJack 21 is a card game where you (at the bottom) play against the dealer (at the topwho represents the casino).\n" +
                        "You have to know that every card with a letter are valued at 10, an Ace can be 1 or 11 and all the others are valued as the number on the card.\n" +
                        "At the very start you receive two card and the Dealer too but you can only see the Dealer's first card.\n" +
                        "Then you can play, your purpose during a round is to have a hand value superior to the dealer's without exceed 21.\n" +
                        "You have to know that the Dealer play like a robot, he have to hit while he is not above or at 17\n" +
                        "Plus, when you have 21 with your first two cards you have what we call a BlackJack so you earn 1,5x your bet\n"+
                        "When you win you earn 1x your bet\n" +
                        "You can hit which means take a card from the card stack.\n" +
                        "You can stand which means don't take a card and wait for the Dealer to play."
        );
    }
}
