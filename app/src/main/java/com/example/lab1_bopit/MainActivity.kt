package com.example.lab1_bopit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intentGame = Intent(this, Jugar::class.java)
        val gameButton = findViewById<Button>(R.id.button_Jugar)

        gameButton.setOnClickListener{
            finish();
            startActivity(intentGame);
        }
        val intentPreference = Intent(this, Preferencias::class.java)
        val preferenceButton = findViewById<Button>(R.id.button_Preferencias)

        preferenceButton.setOnClickListener{
            finish();
            startActivity(intentPreference);
        }

        val intentAbout = Intent(this, About::class.java)
        val aboutButton = findViewById<Button>(R.id.button_About)

        aboutButton.setOnClickListener{
            finish();
            startActivity(intentAbout);
        }

    }
}