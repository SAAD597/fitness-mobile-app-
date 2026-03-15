package com.example.smartfitnesscoach;

import java.util.Random;

/**
 * Concrete implementation for Strength workouts.
 */
public class StrengthWorkout extends BaseWorkout {
    private Exercise[] allExercises = {
        new Exercise("Push-ups", "15 repetitions", R.drawable.img_pushups),
        new Exercise("Squats", "20 repetitions", R.drawable.img_squats),
        new Exercise("Plank", "45 seconds", R.drawable.img_plank),
        new Exercise("Lunges", "12 per leg", R.drawable.img_lunges),
        new Exercise("Pull-ups", "5 repetitions", R.drawable.img_pullups),
        new Exercise("Dips", "10 repetitions", R.drawable.img_dips)
    };

    public StrengthWorkout() {
        super("Strength");
    }

    @Override
    protected void populateExercises() {
        exercises.clear();
        Random random = new Random();
        int count = intensity.equalsIgnoreCase("Beginner") ? 3 : 
                    intensity.equalsIgnoreCase("Intermediate") ? 4 : 5;
        
        for (int i = 0; i < count; i++) {
            Exercise ex = allExercises[random.nextInt(allExercises.length)];
            if (!exercises.contains(ex)) {
                exercises.add(ex);
            } else {
                i--;
            }
        }
    }
}
