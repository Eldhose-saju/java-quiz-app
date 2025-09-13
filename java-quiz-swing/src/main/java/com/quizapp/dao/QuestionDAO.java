package com.quizapp.dao;

import com.quizapp.database.DatabaseConnection;
import com.quizapp.model.Question;
import com.quizapp.model.QuizCategory;
import com.quizapp.util.ArrayShuffler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for managing quiz questions
 */
public class QuestionDAO extends DatabaseConnection {
    
    /**
     * Retrieves questions for a specific quiz category
     * @param category The quiz category to retrieve questions for
     * @return List of questions, or null if database connection fails
     */
    public List<Question> getQuestionsByCategory(QuizCategory category) {
        Connection connection = getConnection();
        if (connection == null) {
            return null;
        }
        
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT question, response1, response2, response3, response4 FROM questions WHERE type = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, category.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Question question = new Question();
                question.setQuestionText(resultSet.getString("question"));
                
                // Get original responses in order
                String[] originalResponses = {
                    resultSet.getString("response1"),
                    resultSet.getString("response2"), 
                    resultSet.getString("response3"),
                    resultSet.getString("response4")
                };
                
                // Shuffle the responses and set them
                question.setOptions(ArrayShuffler.shuffleArray(originalResponses));
                // The correct answer is always the first response from database
                question.setCorrectAnswer(resultSet.getString("response1"));
                
                questions.add(question);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving questions: " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
        
        return questions;
    }
}