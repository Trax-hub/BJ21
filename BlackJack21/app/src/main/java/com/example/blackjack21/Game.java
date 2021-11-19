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

    public Dealer getDealer(){
        return this.dealer;
    }

    public CardStack getStack(){
        return this.stack;
    }


    public void hit(Player player){
        if(!player.isStand() && !player.getHand().isBusted()) {
            player.getHand().add(stack.getRandomCard());
        }
        if(player.getHand().isBusted()){
            stand(this.player);
        }
    }

    public void stand(Player player){
        player.setStand(true);
        dealer.play(stack);
        result();
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

    public void result(){
        if(player.getHand().getValue() == 21 && player.getHand().getcardList().size() == 2){
            player.setBalance(player.getBalance() + (2.5 * player.getBet()));
        }
        if(!player.getHand().isBusted() && !dealer.getHand().isBusted()){
            if(player.getHand().getValue() > player.getHand().getValue()){
                player.setBalance(player.getBalance() + (2 * player.getBet()));
            }
            if(player.getHand().getValue() == player.getHand().getValue()){
                player.setBalance(player.getBalance() + player.getBet());
            }
        }
    }



}
