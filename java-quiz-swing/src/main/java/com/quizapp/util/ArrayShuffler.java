package com.quizapp.util;

import java.security.SecureRandom;

/**
 * Utility class for shuffling arrays using secure random
 */
public class ArrayShuffler {
    
    private static final SecureRandom random = new SecureRandom();
    
    /**
     * Shuffles the given array and returns a new shuffled array
     * @param input The array to shuffle
     * @return A new shuffled array
     */
    public static String[] shuffleArray(String[] input) {
        if (input == null || input.length <= 1) {
            return input.clone();
        }
        
        int[] indices = generateRandomIndices(input.length);
        String[] result = new String[input.length];
        
        for (int i = 0; i < input.length; i++) {
            result[i] = input[indices[i]];
        }
        
        return result;
    }
    
    /**
     * Generates an array of unique random indices
     * @param length The length of indices to generate
     * @return Array of unique random indices
     */
    private static int[] generateRandomIndices(int length) {
        int[] indices = new int[length];
        boolean[] used = new boolean[length];
        
        for (int i = 0; i < length; i++) {
            int randomIndex;
            do {
                randomIndex = random.nextInt(length);
            } while (used[randomIndex]);
            
            indices[i] = randomIndex;
            used[randomIndex] = true;
        }
        
        return indices;
    }
}