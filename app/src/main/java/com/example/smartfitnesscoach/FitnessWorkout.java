package com.example.smartfitnesscoach;

import java.util.Random;

/**
 * Concrete implementation for General Fitness workouts.
 */
public class FitnessWorkout extends BaseWorkout {
    private Exercise[] allExercises = {
        new Exercise("Stretching", "5 minutes", R.drawable.ic_exercise),
        new Exercise("Push-ups", "10 repetitions", R.drawable.img_pushups),
        new Exercise("Walking Lunges", "20 steps", R.drawable.img_lunges),
        new Exercise("Bicycle Crunches", "20 repetitions", R.drawable.ic_exercise),
        new Exercise("Wall Sit", "30 seconds", R.drawable.ic_exercise),
        new Exercise("Side Plank", "30 seconds each side", R.drawable.ic_exercise)
    };

    public FitnessWorkout() {
        super("General Fitness");
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
