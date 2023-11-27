package com.mygdx.game;

public class Question {
    private String text;
    private String correctAnswer;
    private String[] answers;

    public Question(String text, String correctAnswer, String[] answers) {
        this.text = text;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public String[] getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isAnswerCorrect(String selectedOption) {
        return selectedOption.equals(correctAnswer);
    }
}
