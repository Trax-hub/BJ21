package com.example.blackjack21;

public class Player {
    private Hand hand;
    private boolean isStand;
    private double bet;

    public Player(){
        super();
        this.hand = new Hand();
        this.isStand = false;
    }

    public Hand getHand() {
        return this.hand;
    }

    public double getBet(){ return this.bet; }

    public void setBet(double bet){ this.bet = bet;    }

    public boolean isStand(){
        return isStand;
    }

    public void setStand(boolean state){this.isStand = state; }
}