package com.adaptionsoft.games.uglytrivia;

public class Player {

    private final int NUMBER_OF_COINS_TO_WIN_GAME = 6;

    private String name;
    private int place = 0;
    private int coins = 0;
    private boolean isInPenaltyBox = false;

    public Player(String name) {
        this.name = name;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public boolean isInPenaltyBox() {
        return isInPenaltyBox;
    }

    public void setInPenaltyBox(boolean inPenaltyBox) {
        isInPenaltyBox = inPenaltyBox;
    }

    public String getName() {
        return name;
    }

    public void increaseCoins(){
        coins++;
    }

    public boolean didPlayerWin() {
        return (getCoins() == NUMBER_OF_COINS_TO_WIN_GAME);
    }

    public void displayPlace(){
        System.out.println(getName()
                + "'s new location is "
                + getPlace());
    }

    public void displayCoins(){
        System.out.println(getName()
                + " now has "
                + getCoins()
                + " Gold Coins.");
    }

}
