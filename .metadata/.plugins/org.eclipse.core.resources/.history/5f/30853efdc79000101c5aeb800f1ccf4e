package com.quizapp;

import com.formdev.flatlaf.FlatLightLaf;
import com.quizapp.gui.MainFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main application entry point for the Java Quiz Application
 */
public class QuizApplication {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Set modern look and feel
                UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (Exception e) {
                System.err.println("Failed to initialize Look and Feel: " + e.getMessage());
            }
            
            // Create and show the main application window
            new MainFrame().setVisible(true);
        });
    }
}