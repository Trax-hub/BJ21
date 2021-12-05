package com.example.blackjack21;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class User implements Serializable {

    private String pseudo, mail, uid;
    private Double balance;
    private Long rewardTime;

    public User(String pseudo, String mail, String uid, Double balance, Long rewardDate) {
        this.mail = mail;
        this.pseudo = pseudo;
        this.uid = uid;
        this.balance = balance;
        this.rewardTime = rewardDate;
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

    public Long getRewardTime() { return rewardTime;}

}
