package com.example.blackjack21;

public class Dealer {
    private Hand hand;

    public Dealer(){
        this.hand = new Hand();
    }

    public Hand getHand(){ return this.hand;    }

    public void play(){
        while(hand.getValue() < 17){
            hit();
        }
    }

    public void hit(){

    }
}
