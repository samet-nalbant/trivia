package com.adaptionsoft.games.uglytrivia;

import java.util.*;

public class TriviaGame extends Throwable implements Game {

	private static final int MIN_NUMBER_OF_PLAYERS = 2;

	private static final int MAX_NUMBER_OF_PLAYERS = 6;

	private static final int MAX_NUMBER_OF_TILES = 12;

	private static boolean notAWinner;

	private List<Player> players;

	private int currentPlayerIndex = 0;

	private Player currentPlayer;
	boolean isGettingOutOfPenaltyBox;
	private QuestionManager questionManager;

	public TriviaGame(){
		players = new ArrayList<>();
		questionManager = new QuestionManager();
	}

	private boolean isPlayable() {
		return (howManyPlayers() >= MIN_NUMBER_OF_PLAYERS);
	}

	public void addPlayer(String playerName) throws MaxNumberOfUserViolationError {
		if(howManyPlayers() >= MAX_NUMBER_OF_PLAYERS){
			throw new MaxNumberOfUserViolationError("Total player count must be less than " + String.valueOf(MAX_NUMBER_OF_PLAYERS));
		}
		players.add(new Player(playerName));
		displayPlayerAddedInfo(playerName);
	}

	private void displayPlayerAddedInfo(String playerName){
		System.out.println(playerName + " was added");
		System.out.println("He/She is player number " + players.size());
	}

	@Override
	public int getMaximumNumberOfPlayer() {
		return MAX_NUMBER_OF_PLAYERS;
	}

	@Override
	public int getMinimumNumberOfPlayer() {
		return MIN_NUMBER_OF_PLAYERS;
	}

	public int howManyPlayers() {
		return players.size();
	}

	private void roll(int roll) {
		System.out.println(currentPlayer.getName() + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (currentPlayer.isInPenaltyBox()) {
			handlePenaltyBoxRoll(roll);
		}
		if(isGettingOutOfPenaltyBox){
			movePlayer(roll);
			currentPlayer.displayPlace();
			displayCurrentCategory();
			askQuestion();
		}
	}


	private void handlePenaltyBoxRoll(int roll){
		if (isPlayerGettingOutFromPenaltyBox(roll)) {
			isGettingOutOfPenaltyBox = true;
			currentPlayer.setInPenaltyBox(false);
			System.out.println(currentPlayer.getName() + " is getting out of the penalty box");
		} else {
			System.out.println(currentPlayer.getName() + " is not getting out of the penalty box");
			isGettingOutOfPenaltyBox = false;
		}
	}

	private void displayCurrentCategory(){
		System.out.println("The category is " + questionManager.currentCategory(currentPlayer.getPlace()));
	}

	private void movePlayer(int roll){
		int currentPlayerNextPlace = (currentPlayer.getPlace() + roll) % MAX_NUMBER_OF_TILES;
		currentPlayer.setPlace(currentPlayerNextPlace);
	}

	private boolean isPlayerGettingOutFromPenaltyBox(int roll){
		return roll % 2 != 0;
	}

	private void askQuestion() {
		System.out.println(questionManager.nextQuestion(currentPlayer.getPlace()));

	}

	private void handleCorrectAnswerCase() {
		if(!isGettingOutOfPenaltyBox)
			return;
		handleCorrectAnswerCoinOperations();
	}

	private void handleCorrectAnswerCoinOperations(){
		System.out.println("Answer was correct!!!!");
		currentPlayer.increaseCoins();
		currentPlayer.displayCoins();
	}

	public void wrongAnswer(){
		System.out.println("Question was incorrectly answered");
		System.out.println(currentPlayer.getName()+ " was sent to the penalty box");
		currentPlayer.setInPenaltyBox(true);
	}

	@Override
	public void startGame() throws Exception {
		if(!isPlayable()){
			throw new MinNumberOfUserViolationError("Player count must be at least " + String.valueOf(MIN_NUMBER_OF_PLAYERS));
		}

		currentPlayer = players.get(0);

		Random rand = new Random();

		while (!currentPlayer.didPlayerWin()){
			int rolled = rand.nextInt(5) + 1;

			roll(rolled);

			if (rand.nextInt(9) == 7) {
				wrongAnswer();
			} else {
				handleCorrectAnswerCase();
			}
			if(currentPlayer.didPlayerWin()){
				break;
			}
			nextPlayer();
			System.out.println("----------------------------");
		}

	}

	private void nextPlayer(){
		currentPlayerIndex++;
		currentPlayer = players.get(currentPlayerIndex % players.size());
	}

}
