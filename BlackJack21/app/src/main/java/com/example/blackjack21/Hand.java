package com.example.blackjack21;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cardList;
    private int value;
    private boolean isBusted;               //Verify if the player's hand got busted (over 21)

    public Hand(){
        super();
        this.cardList = new ArrayList<Card>();
        this.value = 0;
        this.isBusted = false;
    }

    public List getcardList(){
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
        value += card.getValue();

        //Ace handler
        if(card.getValue() == 1){
            if(value > 21){
                value -= 10;
            }
        }

        //Bust Handler
        if(value > 21){
            setIsBusted(true);
        }
    }
}
