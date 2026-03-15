package com.example.smartfitnesscoach;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class WorkoutDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvSubtitle;
    private RecyclerView rvExercises;
    private ExerciseAdapter adapter;
    private Button btnStart;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_DarkSmooth);
        setContentView(R.layout.activity_workout_detail);

        tvTitle = findViewById(R.id.tvWorkoutDetailTitle);
        tvSubtitle = findViewById(R.id.tvWorkoutDetailSubtitle);
        rvExercises = findViewById(R.id.rvWorkoutDetails);
        btnStart = findViewById(R.id.btnStartWorkout);
        btnBack = findViewById(R.id.btnBack);

        String goal = getIntent().getStringExtra("GOAL");
        String time = getIntent().getStringExtra("TIME");
        String intensity = getIntent().getStringExtra("INTENSITY");

        tvTitle.setText(goal + " Workout");
        tvSubtitle.setText(time + " • " + intensity + " Intensity");

        rvExercises.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExerciseAdapter(new ArrayList<>());
        rvExercises.setAdapter(adapter);

        generateAndDisplayWorkout(goal, time, intensity);

        btnBack.setOnClickListener(v -> finish());
        btnStart.setOnClickListener(v -> {
            Toast.makeText(this, "Workout Started! Let's crush it!", Toast.LENGTH_SHORT).show();
            v.animate().scaleX(1.1f).scaleY(1.1f).setDuration(100).withEndAction(() -> v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).start()).start();
        });
    }

    private void generateAndDisplayWorkout(String goal, String time, String intensity) {
        Workout workoutObj = WorkoutGenerator.getWorkout(goal);
        if (workoutObj != null) {
            workoutObj.generateWorkout(time, intensity);
            adapter.setExercises(workoutObj.getExercises());
        }
    }
}
