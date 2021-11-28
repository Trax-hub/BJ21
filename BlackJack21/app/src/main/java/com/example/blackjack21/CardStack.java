package com.example.blackjack21;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardStack {
    private List cardList;

    public CardStack(){
        this.cardList = new ArrayList<Card>();
        addAllTheCards();
    }

    public Card getRandomCard(){
        Random rn = new Random();
        int rnCardIdx = rn.nextInt(52);
        return (Card) this.cardList.get(rnCardIdx);
    }

    public void addAllTheCards(){
        for(int nb = 1; nb <= 13; nb++) {
            cardList.add(new Card(nb, "c"));
            cardList.add(new Card(nb, "d"));
            cardList.add(new Card(nb, "h"));
            cardList.add(new Card(nb, "s"));
        }
    }
}
