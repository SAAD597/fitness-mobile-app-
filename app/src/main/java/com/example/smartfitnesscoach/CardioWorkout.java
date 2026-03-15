package com.example.smartfitnesscoach;

import java.util.Random;

/**
 * Concrete implementation for Cardio workouts.
 */
public class CardioWorkout extends BaseWorkout {
    private Exercise[] allExercises = {
        new Exercise("Jump Rope", "1 minute", R.drawable.img_jump_rope),
        new Exercise("High Knees", "1 minute", R.drawable.img_high_knees),
        new Exercise("Burpees", "15 repetitions", R.drawable.img_burpees),
        new Exercise("Running in place", "2 minutes", R.drawable.img_running),
        new Exercise("Mountain Climbers", "30 seconds", R.drawable.img_mountain_climbers),
        new Exercise("Jumping Jacks", "50 repetitions", R.drawable.img_jumping_jacks)
    };

    public CardioWorkout() {
        super("Cardio");
    }

    @Override
    protected void populateExercises() {
        exercises.clear();
        Random random = new Random();
        int count = intensity.equalsIgnoreCase("Beginner") ? 3 : 
                    intensity.equalsIgnoreCase("Intermediate") ? 4 : 5;
        
        // Randomly select exercises
        for (int i = 0; i < count; i++) {
            Exercise ex = allExercises[random.nextInt(allExercises.length)];
            if (!exercises.contains(ex)) {
                exercises.add(ex);
            } else {
                i--; // Try again to get a unique one
            }
        }
    }
}
