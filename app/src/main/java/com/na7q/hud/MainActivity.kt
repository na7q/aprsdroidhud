package com.na7q.hud

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.os.Handler
import android.os.Looper
import android.os.Build

class MainActivity : AppCompatActivity() {
    private lateinit var sourceText: TextView
    private lateinit var locationText: TextView
    private lateinit var callsignText: TextView
    private lateinit var packetText: TextView
    private lateinit var timestampText: TextView
    private lateinit var commentText: TextView
    private lateinit var speedText: TextView
    private lateinit var courseText: TextView
    private lateinit var symbolText: TextView

    private var lastReceivedTimestamp: Long = 0
    private var incrementingTimestamp: Long = 0
    private val updateHandler = Handler(Looper.getMainLooper())

    // Runnable to update the timestamp every second (in milliseconds)
    private val timestampRunnable = object : Runnable {
        override fun run() {
            if (lastReceivedTimestamp > 0) {
                // Increment timestamp
                incrementingTimestamp = System.currentTimeMillis() - lastReceivedTimestamp
                val seconds = incrementingTimestamp / 1000 // Convert to seconds
                val formattedTime = String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60)
                timestampText.text = "Last Heard: $formattedTime"
            }
            updateHandler.postDelayed(this, 1000) // Update every second
        }
    }

    // Receiver for updates from APRSdroid
    private val aprsDroidReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("MainActivity", "APRSdroid receiver triggered with intent: $intent")

            if (intent != null) {
                // Log all the extras in the intent
                val allExtras = intent.extras
                if (allExtras != null) {
                    for (key in allExtras.keySet()) {
                        Log.d("MainActivity", "Extra key: $key, Value: ${allExtras[key]}")
                    }
                } else {
                    Log.d("MainActivity", "No extras in intent")
                }

                // Extract values
                val source = intent.getStringExtra("org.aprsdroid.app.SOURCE") ?: "N/A"
                val callsign = intent.getStringExtra("org.aprsdroid.app.CALLSIGN") ?: "N/A"
                val packet = intent.getStringExtra("org.aprsdroid.app.PACKET") ?: "N/A"
                val symbol = intent.getStringExtra("org.aprsdroid.app.SYMBOL") ?: "N/A"
                val comment = intent.getStringExtra("org.aprsdroid.app.COMMENT") ?: "N/A"

                // Convert LAT and LON from microdegrees to degrees
                val latMicro = intent.getIntExtra("org.aprsdroid.app.LOCATION_LAT", 0)
                val lonMicro = intent.getIntExtra("org.aprsdroid.app.LOCATION_LON", 0)
                val latitude = latMicro / 1_000_000.0
                val longitude = lonMicro / 1_000_000.0
                val location = "Lat: $latitude, Lon: $longitude"

                val speed = intent.getIntExtra("org.aprsdroid.app.SPEED", 0)
                val course = intent.getIntExtra("org.aprsdroid.app.COURSE", 0)

                // Log the received data
                Log.d("MainActivity", "Received data: source=$source, location=$location, callsign=$callsign, packet=$packet")

                // Update the TextViews with all the received data
                sourceText.text = "$source"
                locationText.text = "$location"
                callsignText.text = "$callsign"
                packetText.text = "$packet"
                commentText.text = "$comment"
                symbolText.text = "$symbol"  // Add symbol if desired

                // Display the speed and course as integers
                speedText.text = "Speed: $speed mph"  // Show speed as an integer (in km/h or desired unit)
                courseText.text = "Course: $course°"  // Show course as an integer (in degrees)

                // Reset timestamp to current time and start incrementing
                lastReceivedTimestamp = System.currentTimeMillis()
                incrementingTimestamp = 0 // Reset incremented timestamp
            }
        }
    }

    // Receiver for updates from APRSReceiver (custom broadcast)
    private val updateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("MainActivity", "onReceive triggered in updateReceiver with intent: $intent")

            // Get the data from the broadcasted intent
            val positionUpdate = intent?.getStringExtra("POSITION_UPDATE")
            if (positionUpdate != null) {
                // Update the UI with the received data
                sourceText.text = "Source: $positionUpdate"
                locationText.text = "Location: $positionUpdate"
                callsignText.text = "Callsign: $positionUpdate"
                packetText.text = "Packet: $positionUpdate"

                // Reset timestamp to current time and start incrementing
                lastReceivedTimestamp = System.currentTimeMillis()
                incrementingTimestamp = 0 // Reset incremented timestamp

                Log.d("MainActivity", "Received position update from APRSReceiver: $positionUpdate")
            } else {
                // If no data is received, show fallback message
                sourceText.text = "Source: No data"
                locationText.text = "Location: No data"
                callsignText.text = "Callsign: No data"
                packetText.text = "Packet: No data"
                Log.d("MainActivity", "Position update from APRSReceiver was null")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the TextViews
        sourceText = findViewById(R.id.sourceText)
        locationText = findViewById(R.id.locationText)
        callsignText = findViewById(R.id.callsignText)
        packetText = findViewById(R.id.packetText)
        timestampText = findViewById(R.id.timestampText)
        commentText = findViewById(R.id.commentText)
        symbolText = findViewById(R.id.symbolText)
        speedText = findViewById(R.id.speedText)  // Add speedText initialization
        courseText = findViewById(R.id.courseText)  // Add courseText initialization

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

        // Register receivers for both actions, with version check
        val aprsDroidFilter = IntentFilter("org.aprsdroid.app.HUD")
        if (Build.VERSION.SDK_INT >= 34 && applicationInfo.targetSdkVersion >= 34) {
            // Register with the export flag for SDK 34+
            registerReceiver(aprsDroidReceiver, aprsDroidFilter, Context.RECEIVER_EXPORTED)
        } else {
            registerReceiver(aprsDroidReceiver, aprsDroidFilter)
        }
        Log.d("MainActivity", "APRSdroidReceiver registered for POSITION action")

        val updateFilter = IntentFilter("com.na7q.hud.POSITION_UPDATE")
        if (Build.VERSION.SDK_INT >= 34 && applicationInfo.targetSdkVersion >= 34) {
            // Register with the export flag for SDK 34+
            registerReceiver(updateReceiver, updateFilter, Context.RECEIVER_EXPORTED)
        } else {
            registerReceiver(updateReceiver, updateFilter)
        }
        Log.d("MainActivity", "UpdateReceiver registered for POSITION_UPDATE action")

        // Start the Runnable to update the timestamp
        updateHandler.post(timestampRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister both receivers to avoid memory leaks when the activity is destroyed
        unregisterReceiver(aprsDroidReceiver)
        unregisterReceiver(updateReceiver)
        Log.d("MainActivity", "Receivers unregistered")

        // Stop the timestamp updates when the activity is destroyed
        updateHandler.removeCallbacks(timestampRunnable)
    }
}
