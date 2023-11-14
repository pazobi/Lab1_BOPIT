package com.example.lab1_bopit

import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class Jugar : AppCompatActivity(), SensorEventListener {

    private lateinit var victorySnFx: MediaPlayer;
    private lateinit var ambientSnFx: MediaPlayer;
    private lateinit var defeatSnFx: MediaPlayer;
    private lateinit var buttonVictory: Button
    private lateinit var buttonDefeat: Button
    private lateinit var buttonOn: Button
    private lateinit var buttonOff: Button

    private lateinit var gestureDetector: GestureDetector

    private val accel = arrayOf(0.0, 0.0, 0.0)

    private lateinit var textViewTouchEvent: TextView
    private val instrucciones = arrayOf("Fling it", "Scroll it", "Down it", "Tap it","Shake it")
    lateinit var gameText:TextView
    val random = Random()
    public var num = 0
    public var siguiente = false
    private var timeGame = 5000L;
    public var currentNumer = 0
    var gameHandler: Handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jugar)

        gestureDetector = GestureDetector(this, GestureListener())

        textViewTouchEvent = findViewById(R.id.textViewTouchEvent)

        buttonOff = findViewById(R.id.button_off)
        buttonOn = findViewById(R.id.button_on)


        ambientSnFx = MediaPlayer.create(applicationContext, R.raw.musica_ambiente)

        ambientSnFx.start();

        ambientSnFx.isLooping = true;

        victorySnFx = MediaPlayer.create(applicationContext, R.raw.victoria)
        defeatSnFx = MediaPlayer.create(applicationContext, R.raw.derrota);

        buttonOff.setOnClickListener(){
            victorySnFx.pause();
            defeatSnFx.pause();
            ambientSnFx.pause();
        }

        buttonOn.setOnClickListener(){
            ambientSnFx.start();
            ambientSnFx.isLooping = true;
        }

        gameText = findViewById<TextView>(R.id.instruction);

        num = Math.abs(random.nextInt(instrucciones.size))
        gameText.text = instrucciones[num]

        val delayMillis = timeGame.toLong()
        gameHandler.postDelayed(nextGameRunnable, delayMillis)

        displayTimerHandler.postDelayed(displayTimerRunnable, displayTimerMillis)

    }

    private val nextGameRunnable = Runnable { PlayGame() }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)

    }

    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onFling(
            e1: MotionEvent, e2: MotionEvent,
            velocityX: Float, velocityY: Float
        ): Boolean {
            textViewTouchEvent.text = "OnFling"
            if(currentNumer == 0 && !siguiente )
            {
                gameWin();
            }
            return true
        }

        override fun onDown(e: MotionEvent): Boolean {
            textViewTouchEvent.text = "OnDown"
            if(currentNumer == 2 && !siguiente )
            {
                gameWin();
            }
            return true
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            textViewTouchEvent.text = "OnSingleTapUp"
            if(currentNumer == 3 && !siguiente )
            {
                gameWin();
            }
            return true
        }


        override fun onScroll(
            e1: MotionEvent, e2: MotionEvent,
            distanceX: Float, distanceY: Float
        ): Boolean {
            textViewTouchEvent.text = "OnScroll"
            if(currentNumer == 1 && !siguiente )
            {
                gameWin();
            }
            return true
        }
    }

    override fun onPause() {
        super.onPause()
        ambientSnFx.pause()
        victorySnFx.pause()
        defeatSnFx.pause()
    }

    private fun PlayGame() {

        if(siguiente)
        {
            siguiente = false;
            gameText.text = instrucciones[currentNumer]
            timeGame -= displayTimerMillis
            displayTime = timeGame.toFloat()/1000
            gameHandler.postDelayed(nextGameRunnable, timeGame)
        }
        else
        {
            gameText.setTextColor(Color.RED)
            val value = 1000
            val delayMillis = value.toLong()

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }, delayMillis)
        }

    }

    private fun gameWin()
    {
        gameText.setTextColor(Color.GREEN)
        siguiente = true

        currentNumer = Math.abs(random.nextInt(instrucciones.size))

        gameHandler.removeCallbacks(nextGameRunnable)
        gameHandler.postDelayed(nextGameRunnable, 100)
    }

    var displayTime: Float = timeGame.toFloat()/1000
    val displayTimerMillis: Long = 100
    val displayTimerHandler: Handler = Handler(Looper.getMainLooper())
    private val displayTimerRunnable = Runnable {

        displayTime -= 0.1f;
        displayTime = Math.max(displayTime, 0f)
        callDisplayTime()
    }

    private fun callDisplayTime()
    {
        displayTimerHandler.postDelayed(displayTimerRunnable, displayTimerMillis)
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val alpha: Float = 0.8f
        var gravity = 9.8;

        if(p0 != null){
            var gravity_1 = alpha * gravity + (1 - alpha) * p0.values[0]
            var gravity_2 = alpha * gravity + (1 - alpha) * p0.values[1]
            var gravity_3 = alpha * gravity + (1 - alpha) * p0.values[2]

            // Remove the gravity contribution with the high-pass filter.
            accel[0] = p0.values[0] - gravity_1
            accel[1] = p0.values[1] - gravity_2
            accel[2] = p0.values[2] - gravity_3

            if(accel[1] > 0.5f)
            {
                if(currentNumer == 4 && !siguiente)
                {
                    gameWin()
                }
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

}