package com.example.smartfitnesscoach;

import java.util.Random;

/**
 * Concrete implementation for Weight Loss workouts.
 */
public class WeightLossWorkout extends BaseWorkout {
    private Exercise[] allExercises = {
        new Exercise("Burpees", "20 repetitions", R.drawable.img_burpees),
        new Exercise("Mountain Climbers", "1 minute", R.drawable.img_mountain_climbers),
        new Exercise("Shadow Boxing", "3 minutes", R.drawable.img_shadow_boxing),
        new Exercise("Kettlebell Swings", "15 repetitions", R.drawable.img_kettlebell_swings),
        new Exercise("Box Jumps", "10 repetitions", R.drawable.img_box_jumps),
        new Exercise("Plank Jacks", "30 seconds", R.drawable.img_plank_jacks)
    };

    public WeightLossWorkout() {
        super("Weight Loss");
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
