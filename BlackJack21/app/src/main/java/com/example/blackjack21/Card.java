package com.example.blackjack21;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Card {
    private String name;
    private String color;
    private String path;
    private int nb;
    private int value;
    private String res;

    public Card(int nb, String color){
        super();
        this.nb = nb;
        this.color = color;
        this.res = "";
        processRes();
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

    public void setNb(int nb){
        this.nb = nb;
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

    public String getRes(){ return res;    }

    public void setRes(String res){    this.res = res;  }

    @Override
    public String toString() {
        return name + " " + color;
    }

    public void processValue(){
        switch(getNb()){
            case 1 :
                setName("1");
                setValue(1);
                res += 1;
                break;
            case 2 :
                setName("2");
                setValue(2);
                res += 2;
                break;
            case 3 :
                setName("3");
                setValue(3);
                res += 3;
                break;
            case 4 :
                setName("4");
                setValue(4);
                res += 4;
                break;
            case 5 :
                setName("5");
                setValue(5);
                res += 5;
                break;
            case 6 :
                setName("6");
                setValue(6);
                res += 6;
                break;
            case 7 :
                setName("7");
                setValue(7);
                res += 7;
                break;
            case 8 :
                setName("8");
                setValue(8);
                res += 8;
                break;
            case 9 :
                setName("9");
                setValue(9);
                res += 9;
                break;
            case 10 :
                setName("10");
                setValue(10);
                res += 10;
                break;
            case 11 :
                setName("Jack");
                setValue(10);
                res += 11;
                break;
            case 12 :
                setName("Queen");
                setValue(10);
                res += 12;
                break;
            case 13 :
                setName("King");
                setValue(10);
                res += 13;
                break;
        }
    }

    public void processRes(){
        switch (this.color){
            case "Spades" :
                res += "s";
                break;
            case "Diamonds" :
                res += "d";
                break;
            case "Heart" :
                res += "h";
                break;
            case "Clubs" :
                res += "c";
                break;
        }
    }
}
