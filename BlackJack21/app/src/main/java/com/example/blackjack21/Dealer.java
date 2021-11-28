package com.example.blackjack21;

public class Dealer{
    private Hand hand;
    private boolean isStand;

    public Dealer(){
        this.hand = new Hand();
        this.isStand = false;
    }

    public Hand getHand(){ return this.hand;    }

    public void setHand(Hand hand){
        this.hand = hand;
    }

    public void pick(CardStack stack){
        if(!isStand && !this.hand.isBusted()) {
            hand.add(stack.getRandomCard());
            if (hand.getValue() >= 17) {
                setStand(true);
            }
        }
    }

    public void setStand(boolean isStand) { this.isStand = isStand;   }

    public boolean isStand(){ return this.isStand;    }

}
