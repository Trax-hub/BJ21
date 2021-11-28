package com.example.blackjack21;

import java.util.ArrayList;

public class Game {
    private Player player;
    private Dealer dealer;
    private CardStack stack;

    public Game(){
        super();
        this.player = new Player();
        this.dealer = new Dealer();
        this.stack = new CardStack();
    }

    public Player getPlayer(){
        return this.player;
    }

    public Dealer getDealer(){
        return this.dealer;
    }

    public CardStack getStack(){ return this.stack;}

    public ArrayList<Card> getPlayerCardList(){ return this.player.getHand().getcardList(); }

    public ArrayList<Card> getDealerCardList(){ return this.dealer.getHand().getcardList(); }

    public void result(){
        //If the player got a BlackJack
        if((this.player.getHand().getValue() == 21 && this.player.getHand().getcardList().size() == 2)
        && !(this.dealer.getHand().getValue() == 21 && this.dealer.getHand().getcardList().size() == 2)){
            this.player.setLastWin(2.5 * this.player.getBet());
            this.player.setBalance(this.player.getBalance() + this.player.getLastWin());
            return;
        }

        if(!this.player.getHand().isBusted() && !this.dealer.getHand().isBusted()){
            if(this.player.getHand().getValue() > this.dealer.getHand().getValue()) {
                this.player.setLastWin(2 * this.player.getBet());
                this.player.setBalance(this.player.getBalance() + this.player.getLastWin());
                return;
            }
            if(this.player.getHand().getValue() == this.dealer.getHand().getValue()){
                this.player.setLastWin(this.player.getBet());
                this.player.setBalance(this.player.getBalance() + this.player.getLastWin());
                return;
            }else{
                this.player.setLastWin(0);
                this.player.setBalance(this.player.getBalance() + this.player.getLastWin());
                return;
            }

        }

        if(this.dealer.getHand().isBusted() && !this.player.getHand().isBusted()){
            this.player.setLastWin(this.player.getBet() * 2.);
            this.player.setBalance(this.player.getBalance() + this.player.getLastWin());
            return;
        }else if (!this.dealer.getHand().isBusted() && this.player.getHand().isBusted()){
            this.player.setLastWin(0);
            this.player.setBalance(this.player.getBalance() + this.player.getLastWin());
            return;
        }
    }



}
