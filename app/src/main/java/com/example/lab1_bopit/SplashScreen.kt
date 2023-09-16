package com.example.lab1_bopit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ImageView

class SplashScreen : AppCompatActivity() {

    val tagLog = "SplasActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        val value = sharedPreferences.getString("splash_time", "1500")

        Log.i(tagLog, "Value of splash_time:" + value)

        val fadeIn = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)

        val scaleUp = AnimationUtils.loadAnimation(applicationContext, R.anim.scaleup)

        val animationSet = AnimationSet(true)
        animationSet.addAnimation(fadeIn)
        animationSet.addAnimation(scaleUp)

        val  imageView = findViewById<ImageView>(R.id.logo_bopit)

        fadeIn.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?){

                // Animation started
            }

            override fun onAnimationEnd(animation: Animation?){
                val intent =  Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?){
                // Animation repeated
            }
        })
        imageView.startAnimation(fadeIn)
        val seconds = value?.toLong()
        val delayMillis = seconds!!.toLong()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }, delayMillis)
    }

}