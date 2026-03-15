package com.example.smartfitnesscoach;

/**
 * Factory class to instantiate the correct Workout type.
 * Demonstrates the Factory Method pattern and Polymorphism.
 */
public class WorkoutGenerator {
    
    /**
     * Determines which workout class to instantiate depending on the user selection.
     * @param goal The fitness goal selected by the user.
     * @return An instance of a class that implements the Workout interface.
     */
    public static Workout getWorkout(String goal) {
        if (goal == null) return null;
        
        switch (goal) {
            case "Cardio":
                return new CardioWorkout();
            case "Strength":
                return new StrengthWorkout();
            case "Weight Loss":
                return new WeightLossWorkout();
            case "General Fitness":
                return new FitnessWorkout();
            default:
                return new FitnessWorkout(); // Default fallback
        }
    }
}
