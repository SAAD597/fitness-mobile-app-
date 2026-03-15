package com.example.smartfitnesscoach;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a base workout.
 * Demonstrates inheritance and encapsulation.
 */
public abstract class BaseWorkout implements Workout {
    protected String goal;
    protected String time;
    protected String intensity;
    protected List<Exercise> exercises;

    public BaseWorkout(String goal) {
        this.goal = goal;
        this.exercises = new ArrayList<>();
    }

    @Override
    public void generateWorkout(String time, String intensity) {
        this.time = time;
        this.intensity = intensity;
        populateExercises();
    }

    /**
     * Hook method to be implemented by specific workout types.
     * This demonstrates polymorphism as each subclass will provide its own implementation.
     */
    protected abstract void populateExercises();

    @Override
    public List<Exercise> getExercises() {
        return exercises;
    }

    @Override
    public String getWorkoutPlan() {
        StringBuilder sb = new StringBuilder();
        sb.append("Workout Plan Generated\n");
        sb.append("Goal: ").append(goal).append("\n");
        sb.append("Time: ").append(time).append("\n");
        sb.append("Intensity: ").append(intensity).append("\n\n");
        sb.append("Exercises:\n");
        
        for (Exercise exercise : exercises) {
            sb.append("- ").append(exercise.getName()).append(" - ").append(exercise.getDetails()).append("\n");
        }
        
        return sb.toString();
    }
}
