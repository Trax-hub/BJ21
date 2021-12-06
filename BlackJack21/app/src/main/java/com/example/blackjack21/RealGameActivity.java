package com.example.blackjack21;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class RealGameActivity extends AppCompatActivity {

    AppCompatButton hitButton, standButton, doubleButton, betButton, halfBet, doubleBet;
    EditText bet;
    TextView balance, playerValue, dealerValue, result;
    ImageView distributableCard, distributableCard2, stack, claimMoney, score;

    Game game = new Game();

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference mDatabase;


    //Lists who stocks card images/texts and cards themselves
    ArrayList<TextView> playerText1 = new ArrayList<>();
    ArrayList<TextView> playerText2 = new ArrayList<>();
    ArrayList<TextView> dealerText1 = new ArrayList<>();
    ArrayList<TextView> dealerText2 = new ArrayList<>();
    ArrayList<ImageView> playerImages = new ArrayList<>();
    ArrayList<ImageView> dealerImages = new ArrayList<>();
    ArrayList<ConstraintLayout> dealerCard = new ArrayList<>();
    ArrayList<ConstraintLayout> playerCard = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realgame);

        mDatabase = FirebaseDatabase.getInstance("https://black-jack-21-ede5c-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        //Verifying if the user is connected
        if(firebaseUser == null){
            startActivity(new Intent(this, PortalActivity.class));
            finish();
        }

        loadLayoutElements();
        init();

        //Daily reward
        claimMoney.setOnClickListener(v -> mDatabase.child("Users/" + firebaseUser.getUid() + "/rewardTime").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting rewardTime", task.getException());
            }
            else {
                Log.d("Reward time loaded", String.valueOf(Objects.requireNonNull(task.getResult()).getValue()));
                Long nextClaim = task.getResult().getValue(Long.class);
                Long actualTime = Calendar.getInstance().getTimeInMillis();
                if (nextClaim != null) {
                    if (nextClaim <= actualTime) {
                        Toast.makeText(this, "1000 $ claimed !", Toast.LENGTH_SHORT).show();
                        game.getPlayer().setBalance(game.getPlayer().getBalance() + 1000);
                        updateBalance();
                        refreshBalance();
                        mDatabase.child("Users/" + firebaseUser.getUid() + "/rewardTime").setValue(Calendar.getInstance().getTimeInMillis() + 5 * 60 * 1000);
                    } else {
                        Toast.makeText(this, "Wait " + ((nextClaim - actualTime) / 1000 / 60) + 1 + " min", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }));

        betButton.setOnClickListener(v -> {
            try {
                bet();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        hitButton.setOnClickListener(v -> hit());

        standButton.setOnClickListener(v -> stand());

        doubleButton.setOnClickListener(v -> dobble());

        halfBet.setOnClickListener(v -> {
            if ((TextUtils.isEmpty(bet.getText().toString()) || Double.parseDouble(bet.getText().toString()) <= 0.)) {
                bet.setError("Bet must be over 0");
            }   else {
                    bet.setText(Double.toString(Double.parseDouble(bet.getText().toString()) / 2));
                }
        });

        doubleBet.setOnClickListener(v -> {
            if ((TextUtils.isEmpty(bet.getText().toString()) || Double.parseDouble(bet.getText().toString()) <= 0.)) {
                bet.setError("Bet must be over 0");
            } else {
                if(Double.parseDouble(bet.getText().toString()) * 2 > game.getPlayer().getBalance()){
                    bet.setText(Double.toString(game.getPlayer().getBalance()));
                }else {
                    bet.setText(Double.toString(Double.parseDouble(bet.getText().toString()) * 2));
                }
            }
        });

        score.setOnClickListener(v -> {
            startActivity(new Intent(this, ScoreBoardActivity.class));
        });
    }




    private void bet() throws InterruptedException {
        if((TextUtils.isEmpty(bet.getText().toString()) || Double.parseDouble(bet.getText().toString()) <= 0.)) {
            bet.setError("Bet must be over 0");
            return;
        }
        if(Double.parseDouble(bet.getText().toString()) > game.getPlayer().getBalance()) {
            bet.setError("Not enough money");
            return;
        }

        placeBet();
        init();

        disable(hitButton);
        disable(standButton);
        disable(doubleButton);
        disable(betButton);

        firstDraw();
    }

    private void placeBet() {
        game.getPlayer().setBet(Double.parseDouble(bet.getText().toString()));
        game.getPlayer().setBalance(game.getPlayer().getBalance() - game.getPlayer().getBet());
        updateBalance();
        refreshBalance();
    }

    @SuppressLint("SetTextI18n")
    public void dobble(){
        game.getPlayer().dobble(game.getStack());
        playerAnimation(game.getPlayer().getHand().getcardList().size() - 1);
        balance.setText(Double.toString(game.getPlayer().getBalance()));
        mDatabase.child("Users/" + firebaseUser.getUid() + "/balance").setValue(game.getPlayer().getBalance());
        stand();
    }

    private void hit() {
        if(!game.getPlayer().getHand().isBusted() && !game.getPlayer().isStand()){
            game.getPlayer().pick(game.getStack());
            playerAnimation(game.getPlayer().getHand().getcardList().size() - 1);
            disable(doubleButton);
            disable(hitButton);
            disable(standButton);

            if(game.getPlayer().getHand().isBusted()){
                stand();
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void stand() {
        game.getPlayer().setStand(true);

        loadDealerCard((Card) game.getDealer().getHand().getcardList().get(1), 1);

        int startingNbCard = game.getDealer().getHand().getcardList().size();

        while(!game.getDealer().isStand() && !game.getDealer().getHand().isBusted()){
            game.getDealer().pick(game.getStack());
            playerValue.setText(Integer.toString(game.getPlayer().getHand().getValue()));
        }
        if(!(startingNbCard == game.getDealer().getHand().getcardList().size())){
            dealerAnimation(startingNbCard);
        }else{
            enableBetButton();
        }
        dealerValue.setText(Integer.toString(game.getDealer().getHand().getValue()));

        game.result();
        if(game.getPlayer().getLastWin() > game.getPlayer().getBet()){
            dealerValue.setTextColor(Color.RED);
            playerValue.setTextColor(Color.GREEN);
            result.setVisibility(View.VISIBLE);
            result.setTextColor(Color.GREEN);
            result.setText("You won");
        }else if(game.getPlayer().getLastWin() < game.getPlayer().getBet()){
            result.setVisibility(View.VISIBLE);
            playerValue.setTextColor(Color.RED);
            dealerValue.setTextColor(Color.GREEN);
            result.setTextColor(Color.RED);
            result.setText("You lose");
        }else{
            result.setVisibility(View.VISIBLE);
            result.setTextColor(Color.WHITE);
            result.setText("Draw");
        }

        balance.setText(Double.toString(game.getPlayer().getBalance()));
        mDatabase.child("Users/" + firebaseUser.getUid() + "/balance").setValue(game.getPlayer().getBalance());

        disable(hitButton);
        disable(doubleButton);
        disable(standButton);
    }

    private void enableBetButton() {
        betButton.setBackgroundResource(R.drawable.bet_button);
        betButton.setTextColor(Color.BLACK);
        betButton.setEnabled(true);
    }

    private void loadPlayerCard(Card card, int i){
        playerText1.get(i).setText(card.getName());
        playerText2.get(i).setText(card.getName());
        playerImages.get(i).setImageResource(getResources().getIdentifier(card.getColor(), "drawable", getPackageName()));
        if(card.getColor().equals("h") || card.getColor().equals("d")){
            playerText1.get(i).setTextColor(Color.RED);
            playerText2.get(i).setTextColor(Color.RED);
        }else{
            playerText1.get(i).setTextColor(Color.BLACK);
            playerText2.get(i).setTextColor(Color.BLACK);
        }
    }

    private void loadDealerCard(Card card, int i) {
        dealerText1.get(1).setVisibility(View.VISIBLE);
        dealerText2.get(1).setVisibility(View.VISIBLE);
        dealerImages.get(1).setVisibility(View.VISIBLE);
        dealerCard.get(1).setBackgroundResource(R.drawable.card);
        dealerText1.get(i).setText(card.getName());
        dealerText2.get(i).setText(card.getName());
        dealerImages.get(i).setImageResource(getResources().getIdentifier(card.getColor(), "drawable", getPackageName()));
        if (card.getColor().equals("h") || card.getColor().equals("d")) {
            dealerText1.get(i).setTextColor(Color.RED);
            dealerText2.get(i).setTextColor(Color.RED);
        } else {
            dealerText1.get(i).setTextColor(Color.BLACK);
            dealerText2.get(i).setTextColor(Color.BLACK);
        }
    }

    private void hideSecondCard() {
        dealerCard.get(1).setVisibility(View.VISIBLE);
        dealerCard.get(1).setBackgroundResource(R.drawable.back);
        dealerText1.get(1).setVisibility(View.INVISIBLE);
        dealerText2.get(1).setVisibility(View.INVISIBLE);
        dealerImages.get(1).setVisibility(View.INVISIBLE);
    }

    @SuppressLint("SetTextI18n")
    private void refreshBalance() {
        mDatabase.child("Users").child(firebaseUser.getUid()).child("balance").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting balance", task.getException());
            }
            else {
                Log.d("balance", String.valueOf(Objects.requireNonNull(task.getResult()).getValue()));
                if(task.getResult().getValue() != null) {
                    game.getPlayer().setBalance(Double.parseDouble(task.getResult().getValue().toString()));
                    balance.setText(Double.toString(game.getPlayer().getBalance()));
                }
            }
        });
    }

    private void updateBalance(){
        mDatabase.child("Users/" + firebaseUser.getUid() + "/balance").setValue(game.getPlayer().getBalance());
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

    private void init(){
        //clear Hands
        game.getPlayer().setHand(new Hand());
        game.getDealer().setHand(new Hand());

        //Hide card's layouts
        for (ConstraintLayout card : playerCard){
            card.setVisibility(View.INVISIBLE);
        }
        for (ConstraintLayout card : dealerCard){
            card.setVisibility(View.INVISIBLE);
        }

        //Uncolor the values
        dealerValue.setTextColor(Color.WHITE);
        playerValue.setTextColor(Color.WHITE);

        //Hide and uncolor the result
        result.setVisibility(View.INVISIBLE);
        result.setTextColor(Color.WHITE);

        //Actualize balance
        game.getPlayer().setStand(false);
        game.getDealer().setStand(false);
        refreshBalance();
    }

    @SuppressLint("SetTextI18n")
    private void firstDraw() {
        game.getPlayer().pick(game.getStack());
        game.getPlayer().pick(game.getStack());
        game.getDealer().pick(game.getStack());
        dealerValue.setText(Integer.toString(game.getDealer().getHand().getValue()));
        game.getDealer().pick(game.getStack());

        if((game.getPlayer().getHand().getValue() == 21 && game.getPlayer().getHand().getcardList().size() == 2)
                && !(game.getDealer().getHand().getValue() == 21 && game.getDealer().getHand().getcardList().size() == 2)){
            stand();
        }

        playerAnimation(0);
        dealerAnimation(0);
    }

    private void loadLayoutElements() {
        //Buttons
        hitButton = findViewById(R.id.hitButton);
        standButton = findViewById(R.id.standButton);
        doubleButton = findViewById(R.id.doubleButton);
        betButton = findViewById(R.id.betButton);
        halfBet = findViewById(R.id.halfBet);
        doubleBet = findViewById(R.id.doubleBet);

        //TextView
        balance = findViewById(R.id.balance);
        playerValue = findViewById(R.id.playerValue);
        dealerValue = findViewById(R.id.dealerValue);
        result = findViewById(R.id.result);

        //Bet (EditText)
        bet = findViewById(R.id.bet);

        //Stack cards
        distributableCard = findViewById(R.id.distributableCard);
        distributableCard2 = findViewById(R.id.distributableCard2);
        stack = findViewById(R.id.stack);

        //Cards
        //Player cards layouts
        playerCard.add(findViewById(R.id.player_card_1));
        playerCard.add(findViewById(R.id.player_card_2));
        playerCard.add(findViewById(R.id.player_card_3));
        playerCard.add(findViewById(R.id.player_card_4));
        playerCard.add(findViewById(R.id.player_card_5));
        playerCard.add(findViewById(R.id.player_card_6));
        playerCard.add(findViewById(R.id.player_card_7));

        //Dealer cards layouts
        dealerCard.add(findViewById(R.id.dealer_card_1));
        dealerCard.add(findViewById(R.id.dealer_card_2));
        dealerCard.add(findViewById(R.id.dealer_card_3));
        dealerCard.add(findViewById(R.id.dealer_card_4));
        dealerCard.add(findViewById(R.id.dealer_card_5));
        dealerCard.add(findViewById(R.id.dealer_card_6));
        dealerCard.add(findViewById(R.id.dealer_card_7));

        //Player cards Text (values)
        playerText1.add(findViewById(R.id.player_text1_1));
        playerText2.add(findViewById(R.id.player_text1_2));
        playerText1.add(findViewById(R.id.player_text2_1));
        playerText2.add(findViewById(R.id.player_text2_2));
        playerText1.add(findViewById(R.id.player_text3_1));
        playerText2.add(findViewById(R.id.player_text3_2));
        playerText1.add(findViewById(R.id.player_text4_1));
        playerText2.add(findViewById(R.id.player_text4_2));
        playerText1.add(findViewById(R.id.player_text5_1));
        playerText2.add(findViewById(R.id.player_text5_2));
        playerText1.add(findViewById(R.id.player_text6_1));
        playerText2.add(findViewById(R.id.player_text6_2));
        playerText1.add(findViewById(R.id.player_text7_1));
        playerText2.add(findViewById(R.id.player_text7_2));

        //Dealer cards Text (values)
        dealerText1.add(findViewById(R.id.dealer_text1_1));
        dealerText2.add(findViewById(R.id.dealer_text1_2));
        dealerText1.add(findViewById(R.id.dealer_text2_1));
        dealerText2.add(findViewById(R.id.dealer_text2_2));
        dealerText1.add(findViewById(R.id.dealer_text3_1));
        dealerText2.add(findViewById(R.id.dealer_text3_2));
        dealerText1.add(findViewById(R.id.dealer_text4_1));
        dealerText2.add(findViewById(R.id.dealer_text4_2));
        dealerText1.add(findViewById(R.id.dealer_text5_1));
        dealerText2.add(findViewById(R.id.dealer_text5_2));
        dealerText1.add(findViewById(R.id.dealer_text6_1));
        dealerText2.add(findViewById(R.id.dealer_text6_2));
        dealerText1.add(findViewById(R.id.dealer_text7_1));
        dealerText2.add(findViewById(R.id.dealer_text7_2));

        //Player cards image (color)
        playerImages.add(findViewById(R.id.player_card_1_image));
        playerImages.add(findViewById(R.id.player_card_2_image));
        playerImages.add(findViewById(R.id.player_card_3_image));
        playerImages.add(findViewById(R.id.player_card_4_image));
        playerImages.add(findViewById(R.id.player_card_5_image));
        playerImages.add(findViewById(R.id.player_card_6_image));
        playerImages.add(findViewById(R.id.player_card_7_image));

        //Dealer cards Image (color)
        dealerImages.add(findViewById(R.id.dealer_card_image_1));
        dealerImages.add(findViewById(R.id.dealer_card_image_2));
        dealerImages.add(findViewById(R.id.dealer_card_image_3));
        dealerImages.add(findViewById(R.id.dealer_card_image_4));
        dealerImages.add(findViewById(R.id.dealer_card_image_5));
        dealerImages.add(findViewById(R.id.dealer_card_image_6));
        dealerImages.add(findViewById(R.id.dealer_card_image_7));

        //Daily Reward
        claimMoney = findViewById(R.id.claimMoney);

        //ScoreBoard
        score = findViewById(R.id.score);
    }

    private void playerAnimation(int numero) {
        distributableCard.setVisibility(View.VISIBLE);
        distributableCard.bringToFront();
        Path path = new Path();
        path.lineTo(playerCard.get(numero).getX(), playerCard.get(numero).getY());
        ObjectAnimator animator = ObjectAnimator.ofFloat(distributableCard, View.X, View.Y, path);
        animator.setDuration(500);
        animator.addListener(new Animator.AnimatorListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onAnimationEnd(Animator animation) {
                distributableCard.setVisibility(View.INVISIBLE);
                playerCard.get(numero).setVisibility(View.VISIBLE);
                loadPlayerCard(game.getPlayerCardList().get(numero), numero);
                playerValue.setText(Integer.toString(game.getPlayer().getHand().getValue()));

                if(numero < game.getPlayer().getHand().getcardList().size() - 1){
                    playerAnimation(numero + 1);
                }

                if(numero >= 1 && !game.getPlayer().getHand().isBusted()){
                    if(!(game.getPlayer().getHand().getValue() == 21 && game.getPlayerCardList().size() == 2)){
                        enable(standButton);
                        enable(hitButton);
                    }
                }

            }
            @Override
            public void onAnimationCancel(Animator animation) { }
            @Override
            public void onAnimationRepeat(Animator animation) { }
            @Override
            public void onAnimationStart(Animator animation) { }
        });
        animator.start();
    }

    private void dealerAnimation(int numero) {
        distributableCard2.setVisibility(View.VISIBLE);
        distributableCard2.bringToFront();
        Path path = new Path();
        path.lineTo(dealerCard.get(numero).getX(), dealerCard.get(numero).getY());
        ObjectAnimator animator = ObjectAnimator.ofFloat(distributableCard2, View.X, View.Y, path);
        animator.setDuration(500);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onAnimationEnd(Animator animation) {
                distributableCard2.setVisibility(View.INVISIBLE);
                dealerCard.get(numero).setVisibility(View.VISIBLE);
                if(numero == 1){
                    hideSecondCard();
                }else {
                    loadDealerCard(game.getDealerCardList().get(1), 1);
                    loadDealerCard(game.getDealerCardList().get(numero), numero);
                    if(!game.getPlayer().isStand()){
                        enable(standButton);
                        enable(hitButton);
                        if(game.getPlayer().getBalance() >= game.getPlayer().getBet())
                            enable(doubleButton);
                    }
                }
                playerValue.setText(Integer.toString(game.getPlayer().getHand().getValue()));

                if(numero < game.getDealer().getHand().getcardList().size() - 1){
                    dealerAnimation(numero + 1);
                }

                if(game.getPlayer().isStand() && numero == game.getDealer().getHand().getcardList().size() - 1){
                    enableBetButton();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
    }
}