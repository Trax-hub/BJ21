package com.example.blackjack21;

public class Player{
    private Hand hand;
    private boolean isStand;
    private double bet;
    private double balance;
    private double lastWin;

    public Player(){
        this.hand = new Hand();
        this.isStand = false;
        this.balance = 100;
        this.bet = 0;
    }

    public void pick(CardStack stack){
        if(!isStand && !this.hand.isBusted()) {
            hand.add(stack.getRandomCard());
            if (hand.getValue() > 21) {
                setStand(true);
            }
        }
    }

    public Hand getHand() { return this.hand; }

    public double getBet(){ return this.bet; }

    public void setBet(double bet){ this.bet = bet; }

    public void setBalance(double balance){
        double roundBalance = Math.round(balance*100.0)/100.0;
        this.balance = roundBalance; }

    public double getBalance(){ return this.balance; }

    public boolean isStand(){ return isStand; }

    public void setStand(boolean state){this.isStand = state; }

    public void setHand(Hand hand) { this.hand = new Hand(); }

    public void setLastWin(double win){ this.lastWin = win; }

    public double getLastWin(){return this.lastWin; }

    public void dobble(CardStack stack) {
        pick(stack);
        this.balance -= this.bet;
        this.bet = this.bet*2;
        setStand(true);
    }
}