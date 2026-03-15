package com.example.smartfitnesscoach;

/**
 * Workout interface representing the concept of a workout type.
 * This interface defines the contract for all workout implementations.
 */
public interface Workout {
    /**
     * Generates a specific workout routine based on parameters.
     * @param time The duration selected by the user.
     * @param intensity The fitness level selected by the user.
     */
    void generateWorkout(String time, String intensity);

    /**
     * Returns the formatted workout plan as a String.
     * @return The complete workout routine and details.
     */
    String getWorkoutPlan();

    /**
     * Returns the list of exercises for the generated workout.
     * @return A list of Exercise objects.
     */
    java.util.List<Exercise> getExercises();
}
