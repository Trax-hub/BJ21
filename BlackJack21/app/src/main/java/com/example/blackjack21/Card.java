package com.example.blackjack21;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;

public class Card {
    private String name;
    private String color;
    private int nb;
    private int value;

    public Card(int nb, String color){
        this.nb = nb;
        this.color = color;
        processValue();
    }

    public int getValue(){
        return this.value;
    }

    public void setValue(int value){
        this.value = value;
    }

    public int getNb(){
        return this.nb;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getColor(){
        return this.color;
    }

    @Override
    public String toString() {
        return name + " " + color;
    }

    public void processValue(){
        switch(getNb()){
            case 1 :
                setName("1");
                setValue(1);
                break;
            case 2 :
                setName("2");
                setValue(2);
                break;
            case 3 :
                setName("3");
                setValue(3);
                break;
            case 4 :
                setName("4");
                setValue(4);
                break;
            case 5 :
                setName("5");
                setValue(5);
                break;
            case 6 :
                setName("6");
                setValue(6);
                break;
            case 7 :
                setName("7");
                setValue(7);
                break;
            case 8 :
                setName("8");
                setValue(8);
                break;
            case 9 :
                setName("9");
                setValue(9);
                break;
            case 10 :
                setName("10");
                setValue(10);
                break;
            case 11 :
                setName("J");
                setValue(10);
                break;
            case 12 :
                setName("Q");
                setValue(10);
                break;
            case 13 :
                setName("K");
                setValue(10);
                break;
        }
    }
}
