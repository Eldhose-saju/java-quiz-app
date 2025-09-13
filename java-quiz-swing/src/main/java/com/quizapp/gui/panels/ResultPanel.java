package com.quizapp.gui.panels;

import com.quizapp.model.QuizCategory;

import javax.swing.*;
import java.awt.*;

/**
 * Panel for displaying quiz results and performance statistics
 */
public class ResultPanel extends JPanel {
    
    private Runnable backToHomeListener;
    private Runnable retakeQuizListener;
    private QuizCategory lastQuizCategory;
    
    // UI Components
    private JLabel categoryLabel;
    private JLabel scoreLabel;
    private JLabel percentageLabel;
    private JLabel performanceLabel;
    private JProgressBar scoreProgressBar;
    private JLabel emojiLabel;
    
    public ResultPanel() {
        initializeComponents();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(248, 249, 250));
        
        // Header
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Main content
        add(createContentPanel(), BorderLayout.CENTER);
        
        // Footer actions
        add(createFooterPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 58, 64));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Quiz Results", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        categoryLabel = new JLabel("", JLabel.CENTER);
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        categoryLabel.setForeground(new Color(173, 181, 189));
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(categoryLabel, BorderLayout.SOUTH);
        
        return headerPanel;
    }
    
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        
        // Emoji/Icon
        emojiLabel = new JLabel("🎉", JLabel.CENTER);
        emojiLabel.setFont(new Font("Arial", Font.PLAIN, 72));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(emojiLabel, gbc);
        
        // Performance message
        performanceLabel = new JLabel("Excellent!", JLabel.CENTER);
        performanceLabel.setFont(new Font("Arial", Font.BOLD, 20));
        performanceLabel.setForeground(new Color(40, 167, 69));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        contentPanel.add(performanceLabel, gbc);
        
        // Score information
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        
        // Score
        JLabel scoreTitleLabel = new JLabel("Your Score:");
        scoreTitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(scoreTitleLabel, gbc);
        
        scoreLabel = new JLabel("0/0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(new Color(52, 58, 64));
        gbc.gridx = 1;
        gbc.gridy = 2;
        contentPanel.add(scoreLabel, gbc);
        
        // Percentage
        JLabel percentageTitleLabel = new JLabel("Percentage:");
        percentageTitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        contentPanel.add(percentageTitleLabel, gbc);
        
        percentageLabel = new JLabel("0%");
        percentageLabel.setFont(new Font("Arial", Font.BOLD, 24));
        percentageLabel.setForeground(new Color(0, 123, 255));
        gbc.gridx = 1;
        gbc.gridy = 3;
        contentPanel.add(percentageLabel, gbc);
        
        // Progress bar
        scoreProgressBar = new JProgressBar(0, 100);
        scoreProgressBar.setStringPainted(true);
        scoreProgressBar.setString("0%");
        scoreProgressBar.setPreferredSize(new Dimension(300, 25));
        scoreProgressBar.setForeground(new Color(40, 167, 69));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(scoreProgressBar, gbc);
        
        // Performance breakdown
        JPanel breakdownPanel = createPerformanceBreakdown();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 15, 15, 15);
        contentPanel.add(breakdownPanel, gbc);
        
        return contentPanel;
    }
    
    private JPanel createPerformanceBreakdown() {
        JPanel breakdownPanel = new JPanel(new GridLayout(2, 2, 20, 10));
        breakdownPanel.setBackground(new Color(248, 249, 250));
        breakdownPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(206, 212, 218)),
            "Performance Breakdown",
            0, 0,
            new Font("Arial", Font.BOLD, 14),
            new Color(52, 58, 64)
        ));
        breakdownPanel.setPreferredSize(new Dimension(400, 100));
        
        // This will be populated when results are set
        return breakdownPanel;
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout());
        footerPanel.setBackground(new Color(248, 249, 250));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton homeButton = new JButton("Back to Home");
        homeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        homeButton.setPreferredSize(new Dimension(150, 40));
        homeButton.setFocusPainted(false);
        homeButton.addActionListener(e -> {
            if (backToHomeListener != null) {
                backToHomeListener.run();
            }
        });
        
        JButton retakeButton = new JButton("Retake Quiz");
        retakeButton.setFont(new Font("Arial", Font.BOLD, 16));
        retakeButton.setBackground(new Color(0, 123, 255));
        retakeButton.setForeground(Color.WHITE);
        retakeButton.setPreferredSize(new Dimension(150, 40));
        retakeButton.setFocusPainted(false);
        retakeButton.setBorderPainted(false);
        retakeButton.addActionListener(e -> {
            if (retakeQuizListener != null) {
                retakeQuizListener.run();
            }
        });
        
        JButton shareButton = new JButton("Share Results");
        shareButton.setFont(new Font("Arial", Font.PLAIN, 16));
        shareButton.setBackground(new Color(40, 167, 69));
        shareButton.setForeground(Color.WHITE);
        shareButton.setPreferredSize(new Dimension(150, 40));
        shareButton.setFocusPainted(false);
        shareButton.setBorderPainted(false);
        shareButton.addActionListener(e -> shareResults());
        
        footerPanel.add(homeButton);
        footerPanel.add(Box.createHorizontalStrut(10));
        footerPanel.add(retakeButton);
        footerPanel.add(Box.createHorizontalStrut(10));
        footerPanel.add(shareButton);
        
        return footerPanel;
    }
    
    public void setResults(int score, int totalQuestions, QuizCategory category) {
        this.lastQuizCategory = category;
        
        // Update category
        categoryLabel.setText(category.getDisplayName());
        
        // Update score
        scoreLabel.setText(score + "/" + totalQuestions);
        
        // Calculate percentage
        double percentage = totalQuestions > 0 ? (double) score / totalQuestions * 100 : 0;
        int percentageInt = (int) Math.round(percentage);
        percentageLabel.setText(percentageInt + "%");
        
        // Update progress bar
        scoreProgressBar.setValue(percentageInt);
        scoreProgressBar.setString(percentageInt + "%");
        
        // Update performance message and emoji
        updatePerformanceDisplay(percentage);
        
        // Update progress bar color based on performance
        updateProgressBarColor(percentage);
    }
    
    private void updatePerformanceDisplay(double percentage) {
        if (percentage >= 90) {
            performanceLabel.setText("Outstanding! 🌟");
            performanceLabel.setForeground(new Color(40, 167, 69));
            emojiLabel.setText("🏆");
        } else if (percentage >= 80) {
            performanceLabel.setText("Excellent! 👏");
            performanceLabel.setForeground(new Color(40, 167, 69));
            emojiLabel.setText("🎉");
        } else if (percentage >= 70) {
            performanceLabel.setText("Good Job! 👍");
            performanceLabel.setForeground(new Color(255, 193, 7));
            emojiLabel.setText("😊");
        } else if (percentage >= 60) {
            performanceLabel.setText("Not Bad! 🙂");
            performanceLabel.setForeground(new Color(255, 193, 7));
            emojiLabel.setText("🤔");
        } else if (percentage >= 50) {
            performanceLabel.setText("Keep Trying! 💪");
            performanceLabel.setForeground(new Color(255, 149, 0));
            emojiLabel.setText("😐");
        } else {
            performanceLabel.setText("Need More Practice! 📚");
            performanceLabel.setForeground(new Color(220, 53, 69));
            emojiLabel.setText("😔");
        }
    }
    
    private void updateProgressBarColor(double percentage) {
        if (percentage >= 80) {
            scoreProgressBar.setForeground(new Color(40, 167, 69)); // Green
        } else if (percentage >= 60) {
            scoreProgressBar.setForeground(new Color(255, 193, 7)); // Yellow
        } else {
            scoreProgressBar.setForeground(new Color(220, 53, 69)); // Red
        }
    }
    
    private void shareResults() {
        String scoreText = scoreLabel.getText();
        String percentageText = percentageLabel.getText();
        String categoryText = lastQuizCategory != null ? lastQuizCategory.getDisplayName() : "";
        
        String shareText = String.format(
            "I just completed a %s quiz and scored %s (%s)! 🎯\n\n" +
            "Want to test your Java knowledge too? Try the Java Quiz Application!",
            categoryText, scoreText, percentageText
        );
        
        // Copy to clipboard
        try {
            java.awt.datatransfer.StringSelection stringSelection = 
                new java.awt.datatransfer.StringSelection(shareText);
            java.awt.Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(stringSelection, null);
            
            JOptionPane.showMessageDialog(
                this,
                "Results copied to clipboard! You can now paste and share them.",
                "Shared Successfully",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Unable to copy to clipboard: " + e.getMessage(),
                "Share Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    // Getters and Setters
    public QuizCategory getLastQuizCategory() {
        return lastQuizCategory;
    }
    
    public void setBackToHomeListener(Runnable listener) {
        this.backToHomeListener = listener;
    }
    
    public void setRetakeQuizListener(Runnable listener) {
        this.retakeQuizListener = listener;
    }
}