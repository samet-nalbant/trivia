
package com.adaptionsoft.games.trivia.runner;

import com.adaptionsoft.games.uglytrivia.MaxNumberOfUserViolationError;
import com.adaptionsoft.games.uglytrivia.TriviaGame;


public class GameRunner {

	public static void main(String[] args) throws Exception, MaxNumberOfUserViolationError {
		TriviaGame triviaGame = new TriviaGame();

		triviaGame.addPlayer("Chet");
		triviaGame.addPlayer("Pat");
		triviaGame.addPlayer("Sue");

		triviaGame.startGame();
	}
}
