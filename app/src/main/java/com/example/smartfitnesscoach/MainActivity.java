package com.example.smartfitnesscoach;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Main Controller for the Smart Fitness Coach application.
 * Demonstrates the implementation of user interactions and integration with the logic layer.
 */
public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private Spinner goalSpinner;
    private Spinner timeSpinner;
    private Spinner intensitySpinner;
    private Button generateButton;
    private RecyclerView exercisesRecyclerView;
    private ExerciseAdapter exerciseAdapter;
    private StreakManager streakManager;
    private TextView tvStreakCount, tvBadge;

    // TTS components
    private TextToSpeech tts;
    private Button btnMute;
    private boolean isMuted = false;

    private TextView tvChallengeName;
    private View challengeLayout;
    private android.widget.ImageView ivChallengeImage;
    private String[] challengeNames = {
            "🌋 The Volcano",
            "⚡ Thunder Mode",
            "🔥 On Fire",
            "💀 No Mercy",
            "🚀 Rocket Launch",
            "🐉 Dragon Slayer",
            "🌊 The Tsunami",
            "🏆 Champion's Call",
            "💥 Big Bang",
            "🎯 Sniper Mode",
            "🦁 Lion's Heart",
            "🌪️ The Tornado"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         // Vérifier si c'est le 1er lancement
    SharedPreferences prefs = getSharedPreferences("FitnessPrefs", MODE_PRIVATE);
    boolean firstLaunch = !prefs.getBoolean("first_launch_done", false);

    if (firstLaunch) {
        startActivity(new Intent(this, WelcomeActivity.class));
        finish();
        return; // ← important : arrêter l'exécution ici
    }

    setContentView(R.layout.activity_main);

    // Afficher le message de bienvenue
    String name = prefs.getString("user_name", "champion");
    showWelcomeMessage(name);

    // ... reste de votre code existant

        // Initialize UI components
        initializeViews();

        exerciseAdapter = new ExerciseAdapter(new ArrayList<>());
        exercisesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        exercisesRecyclerView.setAdapter(exerciseAdapter);

        // Initialize TTS
        tts = new TextToSpeech(this, this);

        // Set up the button click listener
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Bounce animation
                v.animate()
                        .scaleX(0.95f).scaleY(0.95f)
                        .setDuration(80)
                        .withEndAction(() ->
                                v.animate()
                                        .scaleX(1f).scaleY(1f)
                                        .setDuration(80)
                                        .start()
                        ).start();
                handleGenerateWorkout();
            }
        });

        // Set up mute button listener
        btnMute.setOnClickListener(v -> {
            // Subtle bounce
            v.animate()
                    .scaleX(0.97f).scaleY(0.97f)
                    .setDuration(80)
                    .withEndAction(() ->
                            v.animate()
                                    .scaleX(1f).scaleY(1f)
                                    .setDuration(80)
                                    .start()
                    ).start();
            isMuted = !isMuted;
            btnMute.setText(isMuted ? getString(R.string.coach_off) : getString(R.string.coach_on));
        });

        // Initialize Streak Manager
        streakManager = new StreakManager(this);
        tvStreakCount = findViewById(R.id.tvStreakCount);
        tvBadge = findViewById(R.id.tvBadge);
        updateStreakUI(streakManager.getStreak());

        // Initialize Surprise Button
        Button btnSurprise = findViewById(R.id.btnSurprise);
        tvChallengeName = findViewById(R.id.tvChallengeName);
        challengeLayout = findViewById(R.id.challengeLayout);
        ivChallengeImage = findViewById(R.id.ivChallengeImage);

        btnSurprise.setOnClickListener(v -> {
            // Bounce animation
            v.animate()
                    .scaleX(0.92f).scaleY(0.92f)
                    .setDuration(80)
                    .withEndAction(() ->
                            v.animate()
                                    .scaleX(1f).scaleY(1f)
                                    .setDuration(80)
                                    .start()
                    ).start();

            generateSurpriseWorkout();
        });
    }

    /**
     * Finds and assigns views from the layout.
     */
    private void initializeViews() {
        goalSpinner = findViewById(R.id.goalSpinner);
        timeSpinner = findViewById(R.id.timeSpinner);
        intensitySpinner = findViewById(R.id.intensitySpinner);
        generateButton = findViewById(R.id.generateButton);
        exercisesRecyclerView = findViewById(R.id.exercisesRecyclerView);
        btnMute = findViewById(R.id.btnMute);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.FRENCH);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, getString(R.string.tts_not_supported), Toast.LENGTH_SHORT).show();
            }
            tts.setSpeechRate(0.9f);
        }
    }

    /**
     * Handles the logic for generating and displaying the workout.
     * Demonstrates Polymorphism through the Workout interface.
     */
    private void handleGenerateWorkout() {
        // Collect user selections
        String selectedGoal = goalSpinner.getSelectedItem().toString();
        String selectedTime = timeSpinner.getSelectedItem().toString();
        String selectedIntensity = intensitySpinner.getSelectedItem().toString();

        // generateWorkout(selectedGoal, selectedTime, selectedIntensity); // Removed from here

        android.content.Intent intent = new android.content.Intent(this, WorkoutDetailActivity.class);
        intent.putExtra("GOAL", selectedGoal);
        intent.putExtra("TIME", selectedTime);
        intent.putExtra("INTENSITY", selectedIntensity);
        startActivity(intent);
    }

    /**
     * Core logic to generate a workout based on parameters.
     */
    private void generateWorkout(String selectedGoal, String selectedTime, String selectedIntensity) {
        // Use the Factory pattern to get the appropriate Workout instance
        Workout workoutObj = WorkoutGenerator.getWorkout(selectedGoal);

        if (workoutObj != null) {
            // Generate the workout based on user inputs
            workoutObj.generateWorkout(selectedTime, selectedIntensity);

            // Display the generated plan in the output area
            List<Exercise> exercises = workoutObj.getExercises();
            exerciseAdapter.setExercises(exercises);

            // Announce workout start
            speak(getString(R.string.workout_ready, selectedGoal));

            // Announce exercises
            for (int i = 0; i < exercises.size(); i++) {
                Exercise ex = exercises.get(i);
                speakAdd(getString(R.string.exercise_announcement, i + 1, ex.getName(), ex.getDetails()));
            }

            // Motivation message
            speakAdd(getString(R.string.lets_go));

            // Record workout and update streak
            recordAndCheckStreak();

        } else {
            Toast.makeText(this, "Error: Unable to generate workout.", Toast.LENGTH_SHORT).show();
        }
    }

    private void speak(String text) {
        if (tts != null && !isMuted) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "coach_utterance");
        }
    }

    private void speakAdd(String text) {
        if (tts != null && !isMuted) {
            tts.speak(text, TextToSpeech.QUEUE_ADD, null, "coach_utterance_" + System.currentTimeMillis());
        }
    }

    private void generateSurpriseWorkout() {
        Random random = new Random();

        // Get options from resources
        String[] goals = getResources().getStringArray(R.array.goal_options);
        String[] times = getResources().getStringArray(R.array.time_options);
        String[] intensities = getResources().getStringArray(R.array.intensity_options);

        // Randomize
        String randomGoal = goals[random.nextInt(goals.length)];
        String randomTime = times[random.nextInt(times.length)];
        String randomIntensity = intensities[random.nextInt(intensities.length)];
        String challengeName = challengeNames[random.nextInt(challengeNames.length)];

        // Update Spinners
        goalSpinner.setSelection(indexOf(goals, randomGoal));
        timeSpinner.setSelection(indexOf(times, randomTime));
        intensitySpinner.setSelection(indexOf(intensities, randomIntensity));

        // Show challenge layout
        challengeLayout.setVisibility(View.VISIBLE);
        challengeLayout.setAlpha(0f);
        challengeLayout.setTranslationY(20f);
        challengeLayout.animate().alpha(1f).translationY(0f).setDuration(400).start();

        // Update challenge name and image
        tvChallengeName.setText(challengeName);
        updateChallengeImage(randomGoal);

        // Vocal announcement
        speak("Défi du jour : " + challengeName
                + " ! Objectif " + randomGoal
                + ", " + randomTime
                + ", niveau " + randomIntensity
                + ". C'est parti !");

        // Record workout and update streak
        recordAndCheckStreak();
    }

    private void updateChallengeImage(String goal) {
        int resId = R.drawable.ic_exercise;
        if (goal.contains("Cardio")) resId = R.drawable.img_running;
        else if (goal.contains("Force") || goal.contains("Strength")) resId = R.drawable.img_pushups;
        else if (goal.contains("Flexibility")) resId = R.drawable.img_plank;
        else if (goal.contains("HIIT")) resId = R.drawable.img_burpees;
        
        ivChallengeImage.setImageResource(resId);
    }

    private void recordAndCheckStreak() {
        int newStreak = streakManager.recordWorkout();
        updateStreakUI(newStreak);
        checkMilestone(newStreak);

        // Toast de motivation
        Toast.makeText(this, getMotivationMessage(newStreak), Toast.LENGTH_LONG).show();

        // Vocal announcement for streak
        if (newStreak > 1) {
            speakAdd("Streak : " + newStreak + " jours consécutifs ! " + getBadge(newStreak));
        }
    }

    private void updateStreakUI(int streak) {
        String label = streak + (streak <= 1 ? " jour" : " jours");
        tvStreakCount.setText(label);
        tvBadge.setText(getBadge(streak));

        // Animation rebond sur le feu 🔥
        TextView fire = findViewById(R.id.tvStreakFire);
        fire.animate()
                .scaleX(1.4f).scaleY(1.4f)
                .setDuration(150)
                .withEndAction(() ->
                        fire.animate()
                                .scaleX(1f).scaleY(1f)
                                .setDuration(150)
                                .start()
                ).start();
    }

    private String getBadge(int streak) {
        if (streak >= 30) return "👑 Légende";
        if (streak >= 21) return "🏆 Champion";
        if (streak >= 14) return "💎 Diamant";
        if (streak >= 7) return "🥇 Or";
        if (streak >= 3) return "🥈 Argent";
        if (streak >= 1) return "🥉 Bronze";
        return "";
    }

    private String getMotivationMessage(int streak) {
        if (streak >= 30) return "Tu es une LÉGENDE ! 30 jours non-stop !";
        if (streak >= 21) return "3 semaines de feu ! Incroyable !";
        if (streak >= 14) return "2 semaines ! Tu es inarrêtable !";
        if (streak >= 7) return "1 semaine complète ! Continue !";
        if (streak >= 3) return streak + " jours d'affilée ! Bien joué !";
        return "C'est parti ! Premier jour du streak !";
    }

    private void checkMilestone(int streak) {
        int[] milestones = {3, 7, 14, 21, 30};
        for (int m : milestones) {
            if (streak == m) {
                new android.app.AlertDialog.Builder(this)
                        .setTitle(getBadge(streak) + " Nouveau badge !")
                        .setMessage(getMotivationMessage(streak) + "\n\nTu viens de débloquer le badge " + getBadge(streak) + " !")
                        .setPositiveButton("💪 Merci !", null)
                        .show();
                break;
            }
        }
    }

    private int indexOf(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) return i;
        }
        return 0;
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
private void showWelcomeMessage(String name) {
    TextView welcomeText = findViewById(R.id.tvWelcome);
    if (welcomeText == null) return;

    // Choisir le message selon l'heure
    java.util.Calendar cal = java.util.Calendar.getInstance();
    int hour = cal.get(java.util.Calendar.HOUR_OF_DAY);
    String greeting;
    if (hour < 12)      greeting = "Bonjour";
    else if (hour < 18) greeting = "Bon après-midi";
    else                greeting = "Bonsoir";

    welcomeText.setText(greeting + " " + name + ", prêt pour ta séance ? 🔥");

    // Animation fade-in
    welcomeText.setAlpha(0f);
    welcomeText.animate()
               .alpha(1f)
               .setDuration(800)
               .start();
}
}
