package com.quizapp.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

/**
 * About dialog displaying application information and developer links
 */
public class AboutDialog extends JDialog {
    
    public AboutDialog(JFrame parent) {
        super(parent, "About Java Quiz Application", true);
        initializeDialog();
        createContent();
    }
    
    private void initializeDialog() {
        setSize(500, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void createContent() {
        setLayout(new BorderLayout());
        
        // Header
        add(createHeaderPanel(), BorderLayout.NORTH);
        
        // Main content
        add(createMainPanel(), BorderLayout.CENTER);
        
        // Footer
        add(createFooterPanel(), BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        
        // App icon/logo (using emoji as placeholder)
        JLabel iconLabel = new JLabel("☕", JLabel.CENTER);
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel titleLabel = new JLabel("Java Quiz Application");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel versionLabel = new JLabel("Version 1.0.0");
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        versionLabel.setForeground(new Color(220, 220, 220));
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(iconLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(versionLabel);
        
        return headerPanel;
    }
    
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // Description
        JLabel descriptionLabel = new JLabel("<html><body style='width: 400px; text-align: center;'>" +
                "A comprehensive Java quiz application designed to test your knowledge " +
                "across various Java programming concepts including basics, OOP, " +
                "exception handling, and advanced topics." +
                "</body></html>");
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Features
        JLabel featuresTitle = new JLabel("Features:");
        featuresTitle.setFont(new Font("Arial", Font.BOLD, 16));
        featuresTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        String[] features = {
            "• Multiple quiz categories covering different Java topics",
            "• Timed quizzes with progress tracking",
            "• Instant results and performance analysis",
            "• Modern Swing UI with FlatLaf look and feel",
            "• SQLite database for question storage"
        };
        
        JPanel featuresPanel = new JPanel();
        featuresPanel.setLayout(new BoxLayout(featuresPanel, BoxLayout.Y_AXIS));
        featuresPanel.setBackground(Color.WHITE);
        
        for (String feature : features) {
            JLabel featureLabel = new JLabel(feature);
            featureLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            featureLabel.setBorder(BorderFactory.createEmptyBorder(2, 0, 2, 0));
            featuresPanel.add(featureLabel);
        }
        
        // Technology stack
        JLabel techTitle = new JLabel("Built with:");
        techTitle.setFont(new Font("Arial", Font.BOLD, 16));
        techTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel techLabel = new JLabel("<html><body style='text-align: center;'>" +
                "Java Swing • SQLite • Maven • FlatLaf UI" +
                "</body></html>");
        techLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        techLabel.setForeground(new Color(108, 117, 125));
        techLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        mainPanel.add(descriptionLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(featuresTitle);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(featuresPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(techTitle);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(techLabel);
        
        return mainPanel;
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(248, 249, 250));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Social links panel
        JPanel socialPanel = new JPanel(new FlowLayout());
        socialPanel.setBackground(new Color(248, 249, 250));
        
        JLabel followLabel = new JLabel("Connect with the developer:");
        followLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        socialPanel.add(followLabel);
        
        // Social media buttons
        String[] platforms = {"GitHub", "LinkedIn", "Twitter"};
        String[] urls = {
            "https://github.com",
            "https://linkedin.com",
            "https://twitter.com"
        };
        
        for (int i = 0; i < platforms.length; i++) {
            JButton socialButton = createSocialButton(platforms[i], urls[i]);
            socialPanel.add(socialButton);
        }
        
        // Close button
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(248, 249, 250));
        
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 14));
        closeButton.setPreferredSize(new Dimension(80, 30));
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());
        
        buttonPanel.add(closeButton);
        
        footerPanel.add(socialPanel, BorderLayout.CENTER);
        footerPanel.add(buttonPanel, BorderLayout.EAST);
        
        return footerPanel;
    }
    
    private JButton createSocialButton(String platform, String url) {
        JButton button = new JButton(platform);
        button.setFont(new Font("Arial", Font.PLAIN, 11));
        button.setPreferredSize(new Dimension(70, 25));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWebsite(url);
            }
        });
        
        return button;
    }
    
    private void openWebsite(String url) {
        try {
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(url));
            } else {
                // Fallback: copy URL to clipboard
                java.awt.datatransfer.StringSelection stringSelection = 
                    new java.awt.datatransfer.StringSelection(url);
                java.awt.Toolkit.getDefaultToolkit().getSystemClipboard()
                    .setContents(stringSelection, null);
                
                JOptionPane.showMessageDialog(
                    this,
                    "URL copied to clipboard: " + url,
                    "Browser Not Available",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                this,
                "Unable to open browser. URL: " + url,
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}