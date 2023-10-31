package com.example.lab1_bopit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.MediaPlayer
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast

class Jugar : AppCompatActivity() {

    private lateinit var victorySnFx: MediaPlayer;
    private lateinit var ambientSnFx: MediaPlayer;
    private lateinit var defeatSnFx: MediaPlayer;
    private lateinit var buttonVictory: Button
    private lateinit var buttonDefeat: Button
    private lateinit var buttonOn: Button
    private lateinit var buttonOff: Button

    private lateinit var gestureDetector: GestureDetector

    private lateinit var textViewTouchEvent: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jugar)

        gestureDetector = GestureDetector(this, GestureListener())
        //mDetector.setOnDoubleTapListener(this)

        textViewTouchEvent = findViewById(R.id.textViewTouchEvent)

        buttonOff = findViewById(R.id.button_off)
        buttonOn = findViewById(R.id.button_on)
        buttonVictory=findViewById(R.id.button_victoria)
        buttonDefeat = findViewById(R.id.button_derrota)

        ambientSnFx = MediaPlayer.create(applicationContext, R.raw.musica_ambiente)

        ambientSnFx.start();

        ambientSnFx.isLooping = true;

        victorySnFx = MediaPlayer.create(applicationContext, R.raw.victoria)
        defeatSnFx = MediaPlayer.create(applicationContext, R.raw.derrota);



        buttonVictory.setOnClickListener()
        {
            victorySnFx.start();
            ambientSnFx.pause();
        }

        buttonDefeat.setOnClickListener(){
            defeatSnFx.start();
            ambientSnFx.pause();
        }

        buttonOff.setOnClickListener(){
            victorySnFx.pause();
            defeatSnFx.pause();
            ambientSnFx.pause();
        }

        buttonOn.setOnClickListener(){
            ambientSnFx.start();
            ambientSnFx.isLooping = true;
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)

    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onFling(
            e1: MotionEvent, e2: MotionEvent,
            velocityX: Float, velocityY: Float
        ): Boolean {
            showToast("onFling")
            textViewTouchEvent.text = "OnFling"

            return true
        }

        override fun onDown(e: MotionEvent): Boolean {
            showToast("onDown")
            textViewTouchEvent.text = "OnDown"
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            showToast("onSingleTapUp")
            textViewTouchEvent.text = "OnSingleTapUp"
            return true
        }

        override fun onLongPress(e: MotionEvent) {
            showToast("onLongPress")
            textViewTouchEvent.text = "OnlongPress"

        }

        override fun onScroll(
            e1: MotionEvent, e2: MotionEvent,
            distanceX: Float, distanceY: Float
        ): Boolean {
            showToast("onScroll")
            textViewTouchEvent.text = "OnScroll"
            return true
        }
    }

    override fun onPause() {
        super.onPause()
        ambientSnFx.pause()
        victorySnFx.pause()
        defeatSnFx.pause()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}