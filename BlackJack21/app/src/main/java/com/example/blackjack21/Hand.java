package com.example.blackjack21;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private ArrayList<Card> cardList;
    private int value;
    private boolean isBusted;               //Verify if the hand get busted (over 21)

    public Hand(){
        super();
        this.cardList = new ArrayList<>();
        this.value = 0;
        this.isBusted = false;
    }

    public ArrayList<Card> getcardList(){
        return this.cardList;
    }

    public int getValue(){
        return this.value;
    }

    public boolean isBusted(){
        return isBusted;
    }

    public void setIsBusted(boolean state){
        this.isBusted = state;
    }


    public void add(Card card){
        cardList.add(card);
        this.value += card.getValue();

        //Ace handler
        if(card.getValue() == 1){
            value += 10;
            if(value > 21){
                value -= 10;
            }
        }

        //Bust Handler
        if(this.value > 21){
            setIsBusted(true);
        }
    }
}
