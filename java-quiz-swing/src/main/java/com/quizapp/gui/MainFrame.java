package com.quizapp.gui;

import com.quizapp.model.QuizCategory;
import com.quizapp.gui.panels.HomePanel;
import com.quizapp.gui.panels.QuizPanel;
import com.quizapp.gui.panels.ResultPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Main application frame that manages different panels
 */
public class MainFrame extends JFrame {
    
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private HomePanel homePanel;
    private QuizPanel quizPanel;
    private ResultPanel resultPanel;
    
    public MainFrame() {
        initializeFrame();
        initializePanels();
        setupEventHandlers();
    }
    
    private void initializeFrame() {
        setTitle("Java Quiz Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Custom close operation
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(
                    MainFrame.this,
                    "Are you sure you want to exit?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );
                
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
    
    private void initializePanels() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        homePanel = new HomePanel();
        quizPanel = new QuizPanel();
        resultPanel = new ResultPanel();
        
        mainPanel.add(homePanel, "HOME");
        mainPanel.add(quizPanel, "QUIZ");
        mainPanel.add(resultPanel, "RESULT");
        
        add(mainPanel);
    }
    
    private void setupEventHandlers() {
        // Home panel events
        homePanel.setCategorySelectedListener(this::startQuiz);
        homePanel.setAboutListener(this::showAbout);
        homePanel.setExitListener(this::exitApplication);
        
        // Quiz panel events
        quizPanel.setQuizCompletedListener(this::showResults);
        quizPanel.setBackToHomeListener(this::showHome);
        
        // Result panel events
        resultPanel.setBackToHomeListener(this::showHome);
        resultPanel.setRetakeQuizListener(this::retakeQuiz);
    }
    
    public void startQuiz(QuizCategory category) {
        if (quizPanel.loadQuestions(category)) {
            cardLayout.show(mainPanel, "QUIZ");
        } else {
            JOptionPane.showMessageDialog(
                this,
                "Failed to load questions. Please check your database connection.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    public void showResults(int score, int totalQuestions, QuizCategory category) {
        resultPanel.setResults(score, totalQuestions, category);
        cardLayout.show(mainPanel, "RESULT");
    }
    
    public void showHome() {
        homePanel.resetSelection();
        cardLayout.show(mainPanel, "HOME");
    }
    
    public void retakeQuiz() {
        QuizCategory lastCategory = resultPanel.getLastQuizCategory();
        if (lastCategory != null) {
            startQuiz(lastCategory);
        } else {
            showHome();
        }
    }
    
    public void showAbout() {
        AboutDialog dialog = new AboutDialog(this);
        dialog.setVisible(true);
    }
    
    public void exitApplication() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}