package com.example.smartfitnesscoach;

/**
 * Model class representing a single exercise in a workout plan.
 */
public class Exercise {
    private String name;
    private String details;
    private int imageResourceId;

    public Exercise(String name, String details, int imageResourceId) {
        this.name = name;
        this.details = details;
        this.imageResourceId = imageResourceId;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
