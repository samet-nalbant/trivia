package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class QuestionManager {

    private static final int NUMBER_OF_QUESTIONS = 50;

    private List<Object> popQuestions;
    private List<Object> scienceQuestions;
    private List<Object> sportsQuestions;
    private List<Object> rockQuestions;

    public QuestionManager() {
        popQuestions = new LinkedList<>();
        scienceQuestions = new LinkedList<>();
        sportsQuestions = new LinkedList<>();
        rockQuestions = new LinkedList<>();
        initializeQuestionCategory(QuestionCategory.PopQuestion);
        initializeQuestionCategory(QuestionCategory.RockQuestion);
        initializeQuestionCategory(QuestionCategory.ScienceQuestion);
        initializeQuestionCategory(QuestionCategory.SportsQuestion);
    }

    public void fillAndShuffleQuestionDeck(QuestionCategory questionCategory, List<Object> questionList){
        for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
            questionList.add(questionCategory.toString() + i);
        }
        shuffleQuestionList(questionList);
    }

    public void shuffleQuestionList(List<Object> questionList) {
        List<Object> tempList = new ArrayList<>(questionList);
        Collections.shuffle(tempList);
        questionList.clear();
        questionList.addAll(tempList);
    }

    public void initializeQuestionCategory(QuestionCategory questionCategory){
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

    public String nextQuestion(int currentPlayerPlace){
        List<Object> currentQuestionList = null;
        switch (currentCategory(currentPlayerPlace)){
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
            fillAndShuffleQuestionDeck(currentCategory(currentPlayerPlace), currentQuestionList);
        }

        return ((LinkedList<Object>)currentQuestionList).removeFirst().toString();
    }

    public QuestionCategory currentCategory(int currentPlayerPlace) {
        if (currentPlayerPlace == 0) return QuestionCategory.PopQuestion;
        if (currentPlayerPlace == 4) return QuestionCategory.PopQuestion;
        if (currentPlayerPlace == 8) return QuestionCategory.PopQuestion;
        if (currentPlayerPlace == 1) return QuestionCategory.ScienceQuestion;
        if (currentPlayerPlace == 5) return QuestionCategory.ScienceQuestion;
        if (currentPlayerPlace == 9) return QuestionCategory.ScienceQuestion;
        if (currentPlayerPlace == 2) return QuestionCategory.SportsQuestion;
        if (currentPlayerPlace == 6) return QuestionCategory.SportsQuestion;
        if (currentPlayerPlace == 10) return QuestionCategory.SportsQuestion;
        return QuestionCategory.RockQuestion;
    }

}
