package com.adaptionsoft.games.uglytrivia;

public interface Game {
    void startGame() throws Exception;
    void addPlayer(String playerName) throws MaxNumberOfUserViolationError;
    int getMaximumNumberOfPlayer();
    int getMinimumNumberOfPlayer();
}
