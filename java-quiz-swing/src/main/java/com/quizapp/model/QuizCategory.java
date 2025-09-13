package com.quizapp.model;

/**
 * Enum representing different quiz categories
 */
public enum QuizCategory {
    JAVA_BASIC(1, "Java Basics"),
    OPERATORS_CONDITIONS(2, "Operators & Conditions"),
    ARRAYS_LOOPS(3, "Arrays & Loops"),
    OBJECT_ORIENTED(4, "Object Oriented Programming"),
    POLYMORPHISM_ENUM(5, "Polymorphism & Enums"),
    EXCEPTION_HANDLING(6, "Exception Handling"),
    ADVANCED_JAVA(7, "Advanced Java");
    
    private final int id;
    private final String displayName;
    
    QuizCategory(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }
    
    public int getId() {
        return id;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public static QuizCategory fromId(int id) {
        for (QuizCategory category : values()) {
            if (category.id == id) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown category ID: " + id);
    }
}