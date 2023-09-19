package com.adaptionsoft.games.uglytrivia;

import java.util.*;

public class TriviaGame extends Throwable implements Game {

	private static final int MIN_NUMBER_OF_PLAYER = 2;

	private static final int MAX_NUMBER_OF_PLAYER = 6;

	private static final int MAX_NUMBER_OF_TILE = 12;

	private static final int NUMBER_OF_QUESTIONS = 50;
	private static boolean notAWinner;

	private enum QuestionCategory{
		PopQuestion{
			@Override
			public String toString() {
				return "Pop Question";
			}
		},
		RockQuestion{
			@Override
			public String toString() {
				return "Rock Question";
			}
		},
		SportsQuestion{
			@Override
			public String toString() {
				return "Sports Question";
			}
		},
		ScienceQuestion{
			@Override
			public String toString() {
				return "Science Question";
			}
		}
	}

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
		initializeQuestionCategory(QuestionCategory.PopQuestion);
		initializeQuestionCategory(QuestionCategory.RockQuestion);
		initializeQuestionCategory(QuestionCategory.ScienceQuestion);
		initializeQuestionCategory(QuestionCategory.SportsQuestion);
	}

	private void initializeQuestionCategory(QuestionCategory questionCategory){
		switch (questionCategory){
			case PopQuestion:
				fillAndShuffleQuestionDeck(questionCategory, popQuestions);
				break;
			case RockQuestion:
				fillAndShuffleQuestionDeck(questionCategory, rockQuestions);
				break;
			case ScienceQuestion:
				fillAndShuffleQuestionDeck(questionCategory, scienceQuestions);
				break;
			case SportsQuestion:
				fillAndShuffleQuestionDeck(questionCategory, sportsQuestions);
				break;
		}
	}

	private void fillAndShuffleQuestionDeck(QuestionCategory questionCategory, List<Object> questionList){
		for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
			questionList.add(questionCategory.toString() + i);
		}
		shuffleQuestionList(questionList);
	}

	private void shuffleQuestionList(List<Object> questionList) {
		List<Object> tempList = new ArrayList<>(questionList);
		Collections.shuffle(tempList);
		questionList.clear();
		questionList.addAll(tempList);
	}

	private boolean isPlayable() {
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

	private void roll(int roll) {
		System.out.println(players.get(currentPlayer) + " is the current player");
		System.out.println("They have rolled a " + roll);

		if (inPenaltyBox[currentPlayer]) {
			if (isPlayerGettingOutFromPenaltyBox(roll)) {
				isGettingOutOfPenaltyBox = true;
				inPenaltyBox[currentPlayer] = false;
				System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");

				movePlayer(roll);

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

			movePlayer(roll);

			System.out.println(players.get(currentPlayer)
					+ "'s new location is "
					+ places[currentPlayer]);
			System.out.println("The category is " + currentCategory());
			askQuestion();
		}

	}

	private void movePlayer(int roll){
		places[currentPlayer] = (places[currentPlayer] + roll) % MAX_NUMBER_OF_TILE;
	}

	private boolean isPlayerGettingOutFromPenaltyBox(int roll){
		return roll % 2 != 0;
	}

	private void askQuestion() {
		LinkedList<Object> currentQuestionList = null;
		switch (currentCategory()){
			case SportsQuestion:
				currentQuestionList = sportsQuestions;
				break;
			case PopQuestion:
				currentQuestionList = popQuestions;
				break;
			case RockQuestion:
				currentQuestionList = rockQuestions;
				break;
			case ScienceQuestion:
				currentQuestionList = scienceQuestions;
				break;
		}
		if(currentQuestionList.size() == 0){
			fillAndShuffleQuestionDeck(currentCategory(), currentQuestionList);
		}
		System.out.println(currentQuestionList.removeFirst());

	}

	private QuestionCategory currentCategory() {
		if (places[currentPlayer] == 0) return QuestionCategory.PopQuestion;
		if (places[currentPlayer] == 4) return QuestionCategory.PopQuestion;
		if (places[currentPlayer] == 8) return QuestionCategory.PopQuestion;
		if (places[currentPlayer] == 1) return QuestionCategory.ScienceQuestion;
		if (places[currentPlayer] == 5) return QuestionCategory.ScienceQuestion;
		if (places[currentPlayer] == 9) return QuestionCategory.ScienceQuestion;
		if (places[currentPlayer] == 2) return QuestionCategory.SportsQuestion;
		if (places[currentPlayer] == 6) return QuestionCategory.SportsQuestion;
		if (places[currentPlayer] == 10) return QuestionCategory.SportsQuestion;
		return QuestionCategory.RockQuestion;
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

			int rolled = rand.nextInt(5) + 1;

			roll(rolled);

			if (rand.nextInt(9) == 7) {
				notAWinner = wrongAnswer();
			} else {
				notAWinner = wasCorrectlyAnswered();
			}

			System.out.println("----------------------------");
		} while (notAWinner);

	}
}
