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
import android.graphics.Bitmap //preload
import android.graphics.BitmapFactory //preload

class MainActivity : AppCompatActivity() {
    private lateinit var hudDisplay: HudDisplay

    private var lastReceivedTimestamp: Long = 0
    private var incrementingTimestamp: Long = 0

    private val updateHandler = Handler(Looper.getMainLooper())

	//preload bitmap
    companion object {
        lateinit var iconBitmap: Bitmap
    }

    // Runnable to update the timestamp every second
    private val timestampRunnable = object : Runnable {
        override fun run() {
            // Log system time update
            val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            hudDisplay.updateCurrentTime(currentTime)  // Update the system time

            if (lastReceivedTimestamp > 0) {
                // Increment timestamp
                incrementingTimestamp = System.currentTimeMillis() - lastReceivedTimestamp
                val seconds = incrementingTimestamp / 1000 // Convert to seconds
                val formattedTime = String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60)

                // Log the incremented timestamp

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
		Log.d("MainActivity", "onCreate called")
		
		setContentView(R.layout.activity_main)

        // Preload the allicons.png image here
        iconBitmap = BitmapFactory.decodeResource(resources, R.drawable.allicons)
		
		// Make the app full screen
		Log.d("MainActivity", "Setting app to full screen")
		window.decorView.systemUiVisibility = (
				View.SYSTEM_UI_FLAG_FULLSCREEN or
						View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
						View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				)

		// Keep the screen on while the app is open
		Log.d("MainActivity", "Keeping screen on")
		window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

		// Initialize the SymbolView here
		val symbolImageView: SymbolView = findViewById(R.id.symbolImageView) // Initialize the SymbolView
		Log.d("MainActivity", "SymbolView initialized")

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
			findViewById(R.id.currentTimeText),
			findViewById(R.id.qrgText),
			findViewById(R.id.typeText),			
			symbolImageView // Pass the SymbolView to HudDisplay
		)
		Log.d("MainActivity", "HudDisplay initialized")

		// Register the APRSdroid receiver using AprsReceiver
		aprsReceiver = AprsReceiver { source, location, callsign, packet, comment, symbol, speed, course, model, qrg, type -> 
			// Log received data
			Log.d("MainActivity", "Received APRS data: source=$source, location=$location, callsign=$callsign, packet=$packet, comment=$comment, symbol=$symbol, speed=$speed, course=$course, model=$model, qrg=$qrg")
			
			// Update the HUD with the received data
			hudDisplay.updateHud(source, location, callsign, packet, comment, symbol, speed, course, model, qrg, type)

			// Log timestamp reset and update
			Log.d("MainActivity", "Resetting timestamp to current time")
			lastReceivedTimestamp = System.currentTimeMillis()
			incrementingTimestamp = 0
		}

		// Create an IntentFilter with both actions
		val aprsDroidFilter = IntentFilter().apply {
			addAction("org.aprsdroid.app.HUD")
			addAction("org.na7q.app.HUD")
		}
		
		Log.d("MainActivity", "Registering APRSdroid receiver with IntentFilter")
		if (Build.VERSION.SDK_INT >= 34 && applicationInfo.targetSdkVersion >= 34) {
			Log.d("MainActivity", "Registering receiver with RECEIVER_EXPORTED flag for SDK >= 34")
			registerReceiver(aprsReceiver, aprsDroidFilter, Context.RECEIVER_EXPORTED)
		} else {
			Log.d("MainActivity", "Registering receiver without RECEIVER_EXPORTED flag")
			registerReceiver(aprsReceiver, aprsDroidFilter)
		}

		// Start the Runnable to update the timestamp
		Log.d("MainActivity", "Starting timestamp update runnable")
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
		Log.d("MainActivity", "onDestroy called")
		
        // Unregister the receiver to avoid memory leaks
        Log.d("MainActivity", "Unregistering APRSdroid receiver")
        unregisterReceiver(aprsReceiver)
        
        // Remove the timestamp update handler
        Log.d("MainActivity", "Removing timestamp update handler callbacks")
        updateHandler.removeCallbacks(timestampRunnable)
    }
}
