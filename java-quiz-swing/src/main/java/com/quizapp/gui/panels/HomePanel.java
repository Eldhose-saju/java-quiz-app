package com.quizapp.gui.panels;

import com.quizapp.model.QuizCategory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

/**
 * Home panel displaying quiz categories and application controls
 */
public class HomePanel extends JPanel {
    
    private Consumer<QuizCategory> categorySelectedListener;
    private Runnable aboutListener;
    private Runnable exitListener;
    private ButtonGroup categoryButtonGroup;
    
    public HomePanel() {
        initializeComponents();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 248, 255));
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content
        JPanel contentPanel = createContentPanel();
        add(contentPanel, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Java Quiz Application", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Test your Java knowledge across different topics", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(220, 220, 220));
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        
        return headerPanel;
    }
    
    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(new Color(240, 248, 255));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel instructionLabel = new JLabel("Select a quiz category to get started:");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(instructionLabel, gbc);
        
        // Reset gridwidth for category buttons
        gbc.gridwidth = 1;
        categoryButtonGroup = new ButtonGroup();
        
        // Create category buttons in a 2-column layout
        QuizCategory[] categories = QuizCategory.values();
        for (int i = 0; i < categories.length; i++) {
            JRadioButton categoryButton = createCategoryButton(categories[i]);
            categoryButtonGroup.add(categoryButton);
            
            gbc.gridx = i % 2;
            gbc.gridy = (i / 2) + 1;
            contentPanel.add(categoryButton, gbc);
        }
        
        // Start Quiz button
        JButton startButton = new JButton("Start Quiz");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBackground(new Color(60, 179, 113));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setPreferredSize(new Dimension(200, 40));
        
        startButton.addActionListener(e -> {
            QuizCategory selectedCategory = getSelectedCategory();
            if (selectedCategory != null && categorySelectedListener != null) {
                categorySelectedListener.accept(selectedCategory);
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Please select a quiz category first!",
                    "No Category Selected",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        });
        
        gbc.gridx = 0;
        gbc.gridy = (categories.length / 2) + 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 10, 10);
        contentPanel.add(startButton, gbc);
        
        return contentPanel;
    }
    
    private JRadioButton createCategoryButton(QuizCategory category) {
        JRadioButton button = new JRadioButton(category.getDisplayName());
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(new Color(240, 248, 255));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return button;
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new FlowLayout());
        footerPanel.setBackground(new Color(240, 248, 255));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        JButton aboutButton = new JButton("About");
        aboutButton.setFont(new Font("Arial", Font.PLAIN, 14));
        aboutButton.setFocusPainted(false);
        aboutButton.addActionListener(e -> {
            if (aboutListener != null) {
                aboutListener.run();
            }
        });
        
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 14));
        exitButton.setBackground(new Color(220, 53, 69));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setBorderPainted(false);
        exitButton.addActionListener(e -> {
            if (exitListener != null) {
                exitListener.run();
            }
        });
        
        footerPanel.add(aboutButton);
        footerPanel.add(Box.createHorizontalStrut(10));
        footerPanel.add(exitButton);
        
        return footerPanel;
    }
    
    private QuizCategory getSelectedCategory() {
        for (AbstractButton button : java.util.Collections.list(categoryButtonGroup.getElements())) {
            if (button.isSelected()) {
                String displayName = button.getText();
                for (QuizCategory category : QuizCategory.values()) {
                    if (category.getDisplayName().equals(displayName)) {
                        return category;
                    }
                }
            }
        }
        return null;
    }
    
    public void resetSelection() {
        categoryButtonGroup.clearSelection();
    }
    
    // Listener setters
    public void setCategorySelectedListener(Consumer<QuizCategory> listener) {
        this.categorySelectedListener = listener;
    }
    
    public void setAboutListener(Runnable listener) {
        this.aboutListener = listener;
    }
    
    public void setExitListener(Runnable listener) {
        this.exitListener = listener;
    }
}