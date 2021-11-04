package com.example.blackjack21;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardStack {
    private List cardList;

    public CardStack(){
        super();
        this.cardList = new ArrayList<Card>();
        addAllTheCards();
    }

    public Card getRandomCard(){
        Random rn = new Random();
        int rnCardIdx = rn.nextInt(52) + 1;
        return (Card) this.cardList.get(rnCardIdx);
    }

    public void addAllTheCards(){
        for(int nb = 1; nb <= 13; nb++) {
            cardList.add(new Card(nb, "Clubs"));
            cardList.add(new Card(nb, "Diamonds"));
            cardList.add(new Card(nb, "Heart"));
            cardList.add(new Card(nb, "Spades"));
        }
    }
}
