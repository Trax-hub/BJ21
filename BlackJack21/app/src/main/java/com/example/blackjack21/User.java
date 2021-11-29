package com.example.blackjack21;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class User implements Serializable {

    private String pseudo, mail;
    private String uid;
    private Double balance;
    private Long rewardDate;


    public User(String pseudo, String mail, String uid) {
        this.mail = mail;
        this.pseudo = pseudo;
        this.uid = uid;
        this.balance = 1000.;
        this.rewardDate = Calendar.getInstance().getTimeInMillis();
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

    public Long getRewardTime() { return rewardDate;}

}
