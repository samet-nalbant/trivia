package com.adaptionsoft.games.uglytrivia;

public enum QuestionCategory{
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