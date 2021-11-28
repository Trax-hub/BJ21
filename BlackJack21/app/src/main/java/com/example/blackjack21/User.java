package com.example.blackjack21;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String pseudo, mail;
    private String uid;
    private Double balance;


    public User(String pseudo, String mail, String uid) {
        this.mail = mail;
        this.pseudo = pseudo;
        this.uid = uid;
        this.balance = 100.;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getMail(){
        return mail;
    }

    public String getUid() {
        return uid;
    }

    public Double getBalance() { return balance;}
}
