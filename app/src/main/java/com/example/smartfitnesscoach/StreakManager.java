package com.example.smartfitnesscoach;

import android.content.Context;
import android.content.SharedPreferences;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StreakManager {

    private static final String PREFS   = "streak_prefs";
    private static final String KEY_STREAK    = "current_streak";
    private static final String KEY_LAST_DATE = "last_workout_date";

    private SharedPreferences prefs;

    public StreakManager(Context context) {
        prefs = context.getSharedPreferences(
            PREFS, Context.MODE_PRIVATE);
    }

    // Appelle cette méthode quand l'utilisateur termine une séance
    public int recordWorkout() {
        String today    = getTodayDate();
        String lastDate = prefs.getString(KEY_LAST_DATE, "");
        int streak      = prefs.getInt(KEY_STREAK, 0);

        if (today.equals(lastDate)) {
            // Déjà fait aujourd'hui — streak inchangé
            return streak;
        }

        String yesterday = getYesterdayDate();
        if (lastDate.equals(yesterday)) {
            // Hier = streak continue !
            streak++;
        } else {
            // Rupture — repart à 1
            streak = 1;
        }

        // Sauvegarde
        prefs.edit()
            .putInt(KEY_STREAK, streak)
            .putString(KEY_LAST_DATE, today)
            .apply();

        return streak;
    }

    public int getStreak() {
        return prefs.getInt(KEY_STREAK, 0);
    }

    // Retourne la date d'aujourd'hui au format yyyy-MM-dd
    private String getTodayDate() {
        return new SimpleDateFormat(
            "yyyy-MM-dd", Locale.getDefault())
            .format(new Date());
    }

    // Retourne la date d'hier
    private String getYesterdayDate() {
        Date yesterday = new Date(
            System.currentTimeMillis() - 86400000L);
        return new SimpleDateFormat(
            "yyyy-MM-dd", Locale.getDefault())
            .format(yesterday);
    }
}
