package com.example.smartfitnesscoach;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        EditText nameInput = findViewById(R.id.editTextName);
        Button btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Entre ton prénom !", Toast.LENGTH_SHORT).show();
                return;
            }
            // Sauvegarder le prénom
            SharedPreferences prefs = getSharedPreferences("FitnessPrefs", MODE_PRIVATE);
            prefs.edit()
                 .putString("user_name", name)
                 .putBoolean("first_launch_done", true)
                 .apply();

            // Aller vers MainActivity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}