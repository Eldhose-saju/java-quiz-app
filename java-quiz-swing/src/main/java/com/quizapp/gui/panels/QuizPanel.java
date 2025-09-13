package com.quizapp.gui.panels;

import com.quizapp.dao.QuestionDAO;
import com.quizapp.model.Question;
import com.quizapp.model.QuizCategory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Panel for taking quizzes - displays questions and handles user responses
 */
public class QuizPanel extends JPanel {
    
    private QuizCompletedListener quizCompletedListener;
    private Runnable backToHomeListener;
    
    private List<Question> questions;
    private Map<Integer, String> userAnswers;
    private int currentQuestionIndex;
    private QuizCategory currentCategory;
    
    // UI Components
    private JLabel categoryLabel;
    private JLabel questionNumberLabel;
    private JLabel questionLabel;
    private JProgressBar progressBar;
    private ButtonGroup optionGroup;
    private JRadioButton[] optionButtons;
    private JButton previousButton;
    private JButton nextButton;
    private JButton submitButton;
    private Timer questionTimer;
    private JLabel timerLabel;
    private int timeRemaining;
    
    public QuizPanel() {
        initializeComponents();
        setupTimer();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 250));
        
        // Header
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Main content
        add(createContentPanel(), BorderLayout.CENTER);
        
        // Navigation
        add(createNavigationPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 58, 64));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Category and progress info
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(new Color(52, 58, 64));
        
        categoryLabel = new JLabel();
        categoryLabel.setFont(new Font("Arial", Font.BOLD, 18));
        categoryLabel.setForeground(Color.WHITE);
        
        questionNumberLabel = new JLabel();
        questionNumberLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        questionNumberLabel.setForeground(new Color(173, 181, 189));
        
        infoPanel.add(categoryLabel);
        infoPanel.add(Box.createHorizontalStrut(20));
        infoPanel.add(questionNumberLabel);
        
        // Timer
        timerLabel = new JLabel("Time: 00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timerLabel.setForeground(new Color(255, 193, 7));
        
        // Progress bar
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setString("0%");
        progressBar.setForeground(new Color(40, 167, 69));
        progressBar.setPreferredSize(new Dimension(200, 20));
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(new Color(52, 58, 64));
        rightPanel.add(timerLabel);
        rightPanel.add(Box.createHorizontalStrut(10));
        rightPanel.add(progressBar);
        
        headerPanel.add(infoPanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Question
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        questionLabel.setVerticalAlignment(SwingConstants.TOP);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        // Options
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBackground(Color.WHITE);
        
        optionGroup = new ButtonGroup();
        optionButtons = new JRadioButton[4];
        
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            optionButtons[i].setBackground(Color.WHITE);
            optionButtons[i].setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
            optionButtons[i].setFocusPainted(false);
            
            optionGroup.add(optionButtons[i]);
            optionsPanel.add(optionButtons[i]);
            optionsPanel.add(Box.createVerticalStrut(5));
        }
        
        contentPanel.add(questionLabel, BorderLayout.NORTH);
        contentPanel.add(optionsPanel, BorderLayout.CENTER);
        
        return contentPanel;
    }
    
    private JPanel createNavigationPanel() {
        JPanel navPanel = new JPanel(new BorderLayout());
        navPanel.setBackground(new Color(248, 249, 250));
        navPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Left buttons
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(new Color(248, 249, 250));
        
        JButton backToHomeButton = new JButton("Back to Home");
        backToHomeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backToHomeButton.setFocusPainted(false);
        backToHomeButton.addActionListener(e -> {
            if (confirmBackToHome()) {
                if (backToHomeListener != null) {
                    backToHomeListener.run();
                }
            }
        });
        
        leftPanel.add(backToHomeButton);
        
        // Right buttons
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(new Color(248, 249, 250));
        
        previousButton = new JButton("Previous");
        previousButton.setFont(new Font("Arial", Font.PLAIN, 14));
        previousButton.setFocusPainted(false);
        previousButton.addActionListener(e -> previousQuestion());
        
        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.PLAIN, 14));
        nextButton.setBackground(new Color(0, 123, 255));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setBorderPainted(false);
        nextButton.addActionListener(e -> nextQuestion());
        
        submitButton = new JButton("Submit Quiz");
        submitButton.setFont(new Font("Arial", Font.BOLD, 14));
        submitButton.setBackground(new Color(40, 167, 69));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorderPainted(false);
        submitButton.addActionListener(e -> submitQuiz());
        submitButton.setVisible(false);
        
        rightPanel.add(previousButton);
        rightPanel.add(Box.createHorizontalStrut(10));
        rightPanel.add(nextButton);
        rightPanel.add(submitButton);
        
        navPanel.add(leftPanel, BorderLayout.WEST);
        navPanel.add(rightPanel, BorderLayout.EAST);
        
        return navPanel;
    }
    
    private void setupTimer() {
        questionTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                updateTimerDisplay();
                
                if (timeRemaining <= 0) {
                    questionTimer.stop();
                    JOptionPane.showMessageDialog(
                        QuizPanel.this,
                        "Time's up! Quiz will be submitted automatically.",
                        "Time Up",
                        JOptionPane.WARNING_MESSAGE
                    );
                    submitQuiz();
                }
            }
        });
    }
    
    public boolean loadQuestions(QuizCategory category) {
        QuestionDAO questionDAO = new QuestionDAO();
        questions = questionDAO.getQuestionsByCategory(category);
        
        if (questions == null || questions.isEmpty()) {
            return false;
        }
        
        currentCategory = category;
        currentQuestionIndex = 0;
        userAnswers = new HashMap<>();
        
        // Set timer (30 seconds per question)
        timeRemaining = questions.size() * 30;
        
        updateDisplay();
        questionTimer.start();
        
        return true;
    }
    
    private void updateDisplay() {
        if (questions == null || questions.isEmpty()) {
            return;
        }
        
        // Update header info
        categoryLabel.setText(currentCategory.getDisplayName());
        questionNumberLabel.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
        
        // Update progress
        int progress = (int) ((double) (currentQuestionIndex + 1) / questions.size() * 100);
        progressBar.setValue(progress);
        progressBar.setString(progress + "%");
        
        // Update question and options
        Question currentQuestion = questions.get(currentQuestionIndex);
        questionLabel.setText("<html><body style='width: 600px'>" + 
                             (currentQuestionIndex + 1) + ". " + 
                             currentQuestion.getQuestionText() + "</body></html>");
        
        String[] options = currentQuestion.getOptions();
        for (int i = 0; i < optionButtons.length && i < options.length; i++) {
            optionButtons[i].setText(options[i]);
            optionButtons[i].setVisible(true);
        }
        
        // Hide unused option buttons
        for (int i = options.length; i < optionButtons.length; i++) {
            optionButtons[i].setVisible(false);
        }
        
        // Restore previous answer if exists
        String previousAnswer = userAnswers.get(currentQuestionIndex);
        if (previousAnswer != null) {
            for (JRadioButton button : optionButtons) {
                if (button.getText().equals(previousAnswer)) {
                    button.setSelected(true);
                    break;
                }
            }
        } else {
            optionGroup.clearSelection();
        }
        
        // Update button states
        previousButton.setEnabled(currentQuestionIndex > 0);
        
        if (currentQuestionIndex == questions.size() - 1) {
            nextButton.setVisible(false);
            submitButton.setVisible(true);
        } else {
            nextButton.setVisible(true);
            submitButton.setVisible(false);
        }
    }
    
    private void updateTimerDisplay() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
        
        if (timeRemaining <= 60) {
            timerLabel.setForeground(new Color(220, 53, 69)); // Red warning
        }
    }
    
    private void saveCurrentAnswer() {
        for (JRadioButton button : optionButtons) {
            if (button.isSelected()) {
                userAnswers.put(currentQuestionIndex, button.getText());
                break;
            }
        }
    }
    
    private void previousQuestion() {
        saveCurrentAnswer();
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            updateDisplay();
        }
    }
    
    private void nextQuestion() {
        saveCurrentAnswer();
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            updateDisplay();
        }
    }
    
    private void submitQuiz() {
        saveCurrentAnswer();
        questionTimer.stop();
        
        int score = calculateScore();
        
        if (quizCompletedListener != null) {
            quizCompletedListener.onQuizCompleted(score, questions.size(), currentCategory);
        }
    }
    
    private int calculateScore() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            String userAnswer = userAnswers.get(i);
            if (userAnswer != null && questions.get(i).isCorrectAnswer(userAnswer)) {
                score++;
            }
        }
        return score;
    }
    
    private boolean confirmBackToHome() {
        if (questions != null && !questions.isEmpty()) {
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to go back? Your progress will be lost.",
                "Confirm",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            return result == JOptionPane.YES_OPTION;
        }
        return true;
    }
    
    // Listener interface and setter
    public interface QuizCompletedListener {
        void onQuizCompleted(int score, int totalQuestions, QuizCategory category);
    }
    
    public void setQuizCompletedListener(QuizCompletedListener listener) {
        this.quizCompletedListener = listener;
    }
    
    public void setBackToHomeListener(Runnable listener) {
        this.backToHomeListener = listener;
    }
}