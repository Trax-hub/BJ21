package com.example.blackjack21;

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

    public void setPlayer(Player player){ this.player = player; }

    public Dealer getDealer(){
        return this.dealer;
    }

    public CardStack getStack(){
        return this.stack;
    }


    public void hit(Player player){
        if(player.isStand()) {
            player.getHand().add(stack.getRandomCard());
        }
    }

    public void stand(Player player){
        dealer.play();
    }

    public void dobble(Player player){
        player.getHand().add(stack.getRandomCard());
        player.setStand(true);
    }

    public void split(Player player){
        //TODO
    }

    public void distribute(){
        //Give 2 random cards from the stack to the Dealer
        this.dealer.getHand().add(this.getStack().getRandomCard());
        this.dealer.getHand().add(this.getStack().getRandomCard());

        //Give 2 random cards from the stack to the Player
        this.player.getHand().add(this.getStack().getRandomCard());
        this.player.getHand().add(this.getStack().getRandomCard());
    }



}
