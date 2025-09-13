package com.quizapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Manages SQLite database connections and initialization
 */
public class DatabaseConnection {
    
    private static final String DB_URL = "jdbc:sqlite:quiz.db";
    
    public Connection getConnection() {
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(DB_URL);
            
            // Create tables if they don't exist
            createTablesIfNotExist(connection);
            
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            return null;
        }
    }
    
    private void createTablesIfNotExist(Connection connection) {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS questions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                type INTEGER NOT NULL,
                question TEXT NOT NULL,
                response1 TEXT NOT NULL,
                response2 TEXT NOT NULL,
                response3 TEXT NOT NULL,
                response4 TEXT NOT NULL
            )
        """;
        
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
            
            // Insert sample data if table is empty
            insertSampleData(connection);
            
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }
    
    private void insertSampleData(Connection connection) {
        // Check if data already exists
        String checkDataSQL = "SELECT COUNT(*) FROM questions";
        try (Statement statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(checkDataSQL);
            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return; // Data already exists
            }
        } catch (SQLException e) {
            System.err.println("Error checking existing data: " + e.getMessage());
            return;
        }
        
        // Insert sample questions for each category
        String insertSQL = "INSERT INTO questions (type, question, response1, response2, response3, response4) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (var preparedStatement = connection.prepareStatement(insertSQL)) {
            
            // Java Basic Questions (type = 1)
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "What is the correct way to declare a variable in Java?");
            preparedStatement.setString(3, "int x = 10;");
            preparedStatement.setString(4, "int x == 10;");
            preparedStatement.setString(5, "integer x = 10;");
            preparedStatement.setString(6, "var x = 10;");
            preparedStatement.executeUpdate();
            
            preparedStatement.setInt(1, 1);
            preparedStatement.setString(2, "Which method is the entry point of a Java program?");
            preparedStatement.setString(3, "public static void main(String[] args)");
            preparedStatement.setString(4, "public void main(String[] args)");
            preparedStatement.setString(5, "static void main(String[] args)");
            preparedStatement.setString(6, "public main(String[] args)");
            preparedStatement.executeUpdate();
            
            // Java Operator/Condition Questions (type = 2)
            preparedStatement.setInt(1, 2);
            preparedStatement.setString(2, "What is the result of 5 % 3 in Java?");
            preparedStatement.setString(3, "2");
            preparedStatement.setString(4, "1");
            preparedStatement.setString(5, "0");
            preparedStatement.setString(6, "1.67");
            preparedStatement.executeUpdate();
            
            preparedStatement.setInt(1, 2);
            preparedStatement.setString(2, "Which operator is used for logical AND in Java?");
            preparedStatement.setString(3, "&&");
            preparedStatement.setString(4, "&");
            preparedStatement.setString(5, "AND");
            preparedStatement.setString(6, "||");
            preparedStatement.executeUpdate();
            
            // Java Tables/Loops Questions (type = 3)
            preparedStatement.setInt(1, 3);
            preparedStatement.setString(2, "How do you declare an array in Java?");
            preparedStatement.setString(3, "int[] arr = new int[5];");
            preparedStatement.setString(4, "int arr[] = new int(5);");
            preparedStatement.setString(5, "array int arr = new int[5];");
            preparedStatement.setString(6, "int arr = new int[5];");
            preparedStatement.executeUpdate();
            
            preparedStatement.setInt(1, 3);
            preparedStatement.setString(2, "Which loop is guaranteed to execute at least once?");
            preparedStatement.setString(3, "do-while loop");
            preparedStatement.setString(4, "for loop");
            preparedStatement.setString(5, "while loop");
            preparedStatement.setString(6, "enhanced for loop");
            preparedStatement.executeUpdate();
            
            // Java OOP Questions (type = 4)
            preparedStatement.setInt(1, 4);
            preparedStatement.setString(2, "What is encapsulation in Java?");
            preparedStatement.setString(3, "Hiding implementation details and exposing only necessary parts");
            preparedStatement.setString(4, "Creating multiple classes");
            preparedStatement.setString(5, "Using loops in methods");
            preparedStatement.setString(6, "Declaring variables as public");
            preparedStatement.executeUpdate();
            
            preparedStatement.setInt(1, 4);
            preparedStatement.setString(2, "Which keyword is used for inheritance in Java?");
            preparedStatement.setString(3, "extends");
            preparedStatement.setString(4, "implements");
            preparedStatement.setString(5, "inherits");
            preparedStatement.setString(6, "super");
            preparedStatement.executeUpdate();
            
            // Java Polymorphism/Enum Questions (type = 5)
            preparedStatement.setInt(1, 5);
            preparedStatement.setString(2, "What is method overriding?");
            preparedStatement.setString(3, "Redefining a method in a subclass");
            preparedStatement.setString(4, "Creating multiple methods with same name");
            preparedStatement.setString(5, "Calling a method multiple times");
            preparedStatement.setString(6, "Using static methods");
            preparedStatement.executeUpdate();
            
            preparedStatement.setInt(1, 5);
            preparedStatement.setString(2, "How do you declare an enum in Java?");
            preparedStatement.setString(3, "enum Color { RED, GREEN, BLUE }");
            preparedStatement.setString(4, "enum Color = { RED, GREEN, BLUE }");
            preparedStatement.setString(5, "Color enum = { RED, GREEN, BLUE }");
            preparedStatement.setString(6, "enum { RED, GREEN, BLUE } Color");
            preparedStatement.executeUpdate();
            
            // Java Exception Questions (type = 6)
            preparedStatement.setInt(1, 6);
            preparedStatement.setString(2, "Which block is used to handle exceptions in Java?");
            preparedStatement.setString(3, "try-catch");
            preparedStatement.setString(4, "if-else");
            preparedStatement.setString(5, "switch-case");
            preparedStatement.setString(6, "do-while");
            preparedStatement.executeUpdate();
            
            preparedStatement.setInt(1, 6);
            preparedStatement.setString(2, "What is the parent class of all exceptions in Java?");
            preparedStatement.setString(3, "Throwable");
            preparedStatement.setString(4, "Exception");
            preparedStatement.setString(5, "Error");
            preparedStatement.setString(6, "RuntimeException");
            preparedStatement.executeUpdate();
            
            // Java Advanced Questions (type = 7)
            preparedStatement.setInt(1, 7);
            preparedStatement.setString(2, "What is a lambda expression in Java?");
            preparedStatement.setString(3, "A concise way to represent anonymous functions");
            preparedStatement.setString(4, "A type of variable");
            preparedStatement.setString(5, "A method declaration");
            preparedStatement.setString(6, "A class definition");
            preparedStatement.executeUpdate();
            
            preparedStatement.setInt(1, 7);
            preparedStatement.setString(2, "What does JVM stand for?");
            preparedStatement.setString(3, "Java Virtual Machine");
            preparedStatement.setString(4, "Java Variable Manager");
            preparedStatement.setString(5, "Java Version Manager");
            preparedStatement.setString(6, "Java Visual Machine");
            preparedStatement.executeUpdate();
            
            System.out.println("Sample data inserted successfully!");
            
        } catch (SQLException e) {
            System.err.println("Error inserting sample data: " + e.getMessage());
        }
    }
}