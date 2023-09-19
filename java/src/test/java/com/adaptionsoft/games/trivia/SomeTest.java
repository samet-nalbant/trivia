package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.MaxNumberOfUserViolationError;
import com.adaptionsoft.games.uglytrivia.MinNumberOfUserViolationError;
import com.adaptionsoft.games.uglytrivia.TriviaGame;
import org.junit.Test;

public class SomeTest {

    @Test(expected = MinNumberOfUserViolationError.class)
    public void startGameTestWithLessThanMinimumPlayer() throws Exception, MaxNumberOfUserViolationError {
        Game triviaGame = new TriviaGame();
        for(int i = 0; i < triviaGame.getMinimumNumberOfPlayer() -1; i++){
            triviaGame.addPlayer("Test Player " + String.valueOf(i));
        }
        triviaGame.startGame();
    }

    @Test(expected = MaxNumberOfUserViolationError.class)
    public void startGameTestWithMoreThanMaximumPlayer() throws Exception, MaxNumberOfUserViolationError {
        Game triviaGame = new TriviaGame();
        for(int i = 0; i < triviaGame.getMaximumNumberOfPlayer() + 1; i++){
            triviaGame.addPlayer("Test Player " + String.valueOf(i));
        }
    }
}
