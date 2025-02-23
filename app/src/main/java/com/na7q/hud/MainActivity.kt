package com.na7q.hud

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Build
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var hudDisplay: HudDisplay

    private var lastReceivedTimestamp: Long = 0
    private var incrementingTimestamp: Long = 0

    private val updateHandler = Handler(Looper.getMainLooper())

    // Runnable to update the timestamp every second
    private val timestampRunnable = object : Runnable {
        override fun run() {
            // Update the current system time every second
            val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            hudDisplay.updateCurrentTime(currentTime)  // Update the system time

            if (lastReceivedTimestamp > 0) {
                // Increment timestamp
                incrementingTimestamp = System.currentTimeMillis() - lastReceivedTimestamp
                val seconds = incrementingTimestamp / 1000 // Convert to seconds
                val formattedTime = String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60)

                // Update last heard timestamp
                hudDisplay.updateTimestamp(formattedTime)
            }

            updateHandler.postDelayed(this, 1000) // Update every second
        }
    }

    // APRS receiver instance
    private lateinit var aprsReceiver: AprsReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Log when onCreate is triggered
        Log.d("MainActivity", "onCreate called")

        // Make the app full screen
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )

        // Keep the screen on while the app is open
        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)


        // Initialize the HudDisplay class with the TextViews
        hudDisplay = HudDisplay(
            findViewById(R.id.sourceText),
            findViewById(R.id.locationText),
            findViewById(R.id.callsignText),
            findViewById(R.id.packetText),
            findViewById(R.id.timestampText),
            findViewById(R.id.commentText),
            findViewById(R.id.symbolText),
            findViewById(R.id.speedText),
            findViewById(R.id.courseText),
            findViewById(R.id.toCallText),
            findViewById(R.id.currentTimeText)
        )

        // Register the APRSdroid receiver using AprsReceiver
        aprsReceiver = AprsReceiver { source, location, callsign, packet, comment, symbol, speed, course, model ->
            // Update the HUD with the received data
            hudDisplay.updateHud(source, location, callsign, packet, comment, symbol, speed, course, model)
            
            // Reset timestamp to current time and start incrementing
            lastReceivedTimestamp = System.currentTimeMillis()
            incrementingTimestamp = 0
        }

        val aprsDroidFilter = IntentFilter("org.aprsdroid.app.HUD")
        if (Build.VERSION.SDK_INT >= 34 && applicationInfo.targetSdkVersion >= 34) {
            registerReceiver(aprsReceiver, aprsDroidFilter, Context.RECEIVER_EXPORTED)
        } else {
            registerReceiver(aprsReceiver, aprsDroidFilter)
        }

        // Start the Runnable to update the timestamp
        updateHandler.post(timestampRunnable)

        // Restore saved instance state if available
        savedInstanceState?.let {
            hudDisplay.restoreInstanceState(it)
            lastReceivedTimestamp = it.getLong("lastReceivedTimestamp", 0)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the instance state using HudDisplay's method
        hudDisplay.saveInstanceState(outState)
        outState.putLong("lastReceivedTimestamp", lastReceivedTimestamp)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the receiver to avoid memory leaks
        unregisterReceiver(aprsReceiver)
        updateHandler.removeCallbacks(timestampRunnable)
    }
}
