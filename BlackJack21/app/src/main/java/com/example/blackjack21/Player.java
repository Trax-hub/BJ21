package com.example.blackjack21;

public class Player {
    private Hand hand;
    private boolean isStand;
    private double bet;
    private double balance;

    public Player(){
        super();
        this.hand = new Hand();
        this.isStand = false;
        this.balance = 100;
        this.bet = 0;
    }

    public Hand getHand() {
        return this.hand;
    }

    public double getBet(){ return this.bet; }

    public void setBet(double bet){ this.bet = bet; }

    public void setBalance(double balance){ this.balance = balance; }

    public double getBalance(){ return this.balance; }

    public boolean isStand(){
        return isStand;
    }

    public void setStand(boolean state){this.isStand = state; }

    public void setHand(Hand hand) {
        this.hand = new Hand();
    }
}