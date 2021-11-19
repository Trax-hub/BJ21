package com.example.blackjack21;

public class Dealer {
    private Hand hand;

    public Dealer(){
        this.hand = new Hand();
    }

    public Hand getHand(){ return this.hand;    }

    public void setHand(Hand hand){
        this.hand = hand;
    }

    public void play(CardStack stack){
        while(hand.getValue() < 17){
            hand.add(stack.getRandomCard());
        }
    }

}
