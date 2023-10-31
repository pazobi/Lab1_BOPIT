package com.example.lab1_bopit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val intentGame = Intent(this, Jugar::class.java)
        val gameButton = findViewById<Button>(R.id.button_Jugar)

        gameButton.setOnClickListener{
            startActivity(intentGame);
        }

        val intentAbout = Intent(this, About::class.java)
        val aboutButton = findViewById<Button>(R.id.button_About)

        aboutButton.setOnClickListener{
            finish();
            startActivity(intentAbout);
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbarmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item1->{
                val intentPreferences = Intent(this, Preferencias::class.java)
                startActivity(intentPreferences)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}