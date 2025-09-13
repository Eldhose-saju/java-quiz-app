package com.quizapp.model;

/**
 * Data model representing a quiz question with multiple choice answers
 */
public class Question {
    private String questionText;
    private String[] options;
    private String correctAnswer;

    public Question() {
    }

    public Question(String questionText, String[] options, String correctAnswer) {
        this.questionText = questionText;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
    
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
    public boolean isCorrectAnswer(String answer) {
        return correctAnswer != null && correctAnswer.equals(answer);
    }
}