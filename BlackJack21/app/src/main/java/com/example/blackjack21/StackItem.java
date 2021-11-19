package com.example.blackjack21;

public class StackItem {

    private Card card;

    public StackItem(Card card) {
        this.card = card;
    }

    public String getColor() {
        return card.getColor();
    }

    public String getName(){
        return card.getName();
    }

}