package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class TriviaGame extends Throwable implements Game {

	private static final int MIN_NUMBER_OF_PLAYER = 2;

	private static final int MAX_NUMBER_OF_PLAYER = 6;
	private static boolean notAWinner;


	ArrayList players = new ArrayList();
	int[] places = new int[MAX_NUMBER_OF_PLAYER];
	int[] purses  = new int[MAX_NUMBER_OF_PLAYER];
	boolean[] inPenaltyBox  = new boolean[MAX_NUMBER_OF_PLAYER];

	LinkedList popQuestions = new LinkedList();
	LinkedList scienceQuestions = new LinkedList();
	LinkedList sportsQuestions = new LinkedList();
	LinkedList rockQuestions = new LinkedList();

	int currentPlayer = 0;
	boolean isGettingOutOfPenaltyBox;

	public TriviaGame(){
		for (int i = 0; i < 50; i++) {
			popQuestions.addLast("Pop Question " + i);
			scienceQuestions.addLast(("Science Question " + i));
			sportsQuestions.addLast(("Sports Question " + i));
			rockQuestions.addLast(createRockQuestion(i));
		}
	}

	public String createRockQuestion(int index){
		return "Rock Question " + index;
	}

	public boolean isPlayable() {
		return (howManyPlayers() >= MIN_NUMBER_OF_PLAYER);
	}

	public void addPlayer(String playerName) throws MaxNumberOfUserViolationError {
		if(howManyPlayers() >= MAX_NUMBER_OF_PLAYER){
			throw new MaxNumberOfUserViolationError("Total player count must be less than " + String.valueOf(MAX_NUMBER_OF_PLAYER));
		}
		players.add(playerName);
		int lastPlayerIndex = getLastPlayerIndex();
		places[lastPlayerIndex] = 0;
		purses[lastPlayerIndex] = 0;
		inPenaltyBox[lastPlayerIndex] = false;

		System.out.println(playerName + " was added");
		System.out.println("He/She is player number " + players.size());
	}

	@Override
	public int getMaximumNumberOfPlayer() {
		return MAX_NUMBER_OF_PLAYER;
	}

	@Override
	public int getMinimumNumberOfPlayer() {
		return MIN_NUMBER_OF_PLAYER;
	}


	public int howManyPlayers() {
		return players.size();
	}

	public void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (inPenaltyBox[currentPlayer]) {
			if (roll % 2 != 0) {
				isGettingOutOfPenaltyBox = true;

				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
				places[currentPlayer] = places[currentPlayer] + roll;
				if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

				System.out.println(players.get(currentPlayer)
						+ "'s new location is "
						+ places[currentPlayer]);
				System.out.println("The category is " + currentCategory());
				askQuestion();
			} else {
				System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
				isGettingOutOfPenaltyBox = false;
			}

		} else {

			places[currentPlayer] = places[currentPlayer] + roll;
			if (places[currentPlayer] > 11) places[currentPlayer] = places[currentPlayer] - 12;

			System.out.println(players.get(currentPlayer)
					+ "'s new location is "
					+ places[currentPlayer]);
			System.out.println("The category is " + currentCategory());
			askQuestion();
		}

	}

	private void askQuestion() {
		if (currentCategory() == "Pop")
			System.out.println(popQuestions.removeFirst());
		if (currentCategory() == "Science")
			System.out.println(scienceQuestions.removeFirst());
		if (currentCategory() == "Sports")
			System.out.println(sportsQuestions.removeFirst());
		if (currentCategory() == "Rock")
			System.out.println(rockQuestions.removeFirst());
	}


	private String currentCategory() {
		if (places[currentPlayer] == 0) return "Pop";
		if (places[currentPlayer] == 4) return "Pop";
		if (places[currentPlayer] == 8) return "Pop";
		if (places[currentPlayer] == 1) return "Science";
		if (places[currentPlayer] == 5) return "Science";
		if (places[currentPlayer] == 9) return "Science";
		if (places[currentPlayer] == 2) return "Sports";
		if (places[currentPlayer] == 6) return "Sports";
		if (places[currentPlayer] == 10) return "Sports";
		return "Rock";
	}

	public boolean wasCorrectlyAnswered() {
		if (inPenaltyBox[currentPlayer]){
			if (isGettingOutOfPenaltyBox) {
				System.out.println("Answer was correct!!!!");
				purses[currentPlayer]++;
				System.out.println(players.get(currentPlayer)
						+ " now has "
						+ purses[currentPlayer]
						+ " Gold Coins.");

				boolean winner = didPlayerWin();
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;

				return winner;
			} else {
				currentPlayer++;
				if (currentPlayer == players.size()) currentPlayer = 0;
				return true;
			}



		} else {

			System.out.println("Answer was corrent!!!!");
			purses[currentPlayer]++;
			System.out.println(players.get(currentPlayer)
					+ " now has "
					+ purses[currentPlayer]
					+ " Gold Coins.");

			boolean winner = didPlayerWin();
			currentPlayer++;
			if (currentPlayer == players.size()) currentPlayer = 0;

			return winner;
		}
	}

	public boolean wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(players.get(currentPlayer)+ " was sent to the penalty box");
		inPenaltyBox[currentPlayer] = true;

		currentPlayer++;
		if (currentPlayer == players.size()) currentPlayer = 0;
		return true;
	}


	private boolean didPlayerWin() {
		return !(purses[currentPlayer] == 6);
	}

	private int getLastPlayerIndex(){
		return howManyPlayers()-1;
	}

	@Override
	public void startGame() throws Exception {
		if(!isPlayable()){
			throw new MinNumberOfUserViolationError("Player count must be at least " + String.valueOf(MIN_NUMBER_OF_PLAYER));
		}
		Random rand = new Random();

		do {

			roll(rand.nextInt(5) + 1);

			if (rand.nextInt(9) == 7) {
				notAWinner = wrongAnswer();
			} else {
				notAWinner = wasCorrectlyAnswered();
			}

		} while (notAWinner);

	}
}
