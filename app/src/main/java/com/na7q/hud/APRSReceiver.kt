package com.na7q.hud

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location  // Make sure this is imported
import android.util.Log

class APRSReceiver : BroadcastReceiver() {

    // This will handle the incoming broadcast
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("APRSReceiver", "onReceive triggered")  // Log when the receiver is triggered

        if (intent != null) {
            // Extract relevant data from the intent
            val source = intent.getStringExtra("source") ?: "N/A"

            // Handle the location, check if it's a Location object
            val locationObj = intent.getParcelableExtra<Location>("location")
            val location = if (locationObj != null) {
                "Lat: ${locationObj.latitude}, Lon: ${locationObj.longitude}"
            } else {
                "N/A"
            }

            val callsign = intent.getStringExtra("callsign") ?: "N/A"
            val packet = intent.getStringExtra("packet") ?: "N/A"

            // Create a message to show the data
            val positionMessage = "Source: $source\nLocation: $location\nCallsign: $callsign\nPacket: $packet"

            // Log the received data for debugging
            Log.d("APRSReceiver", "Received data: $positionMessage")

            // Now, let's send a broadcast to MainActivity to update the UI
            val updateIntent = Intent(context, MainActivity::class.java)
            updateIntent.putExtra("POSITION_UPDATE", positionMessage)
            context?.sendBroadcast(updateIntent)

            Log.d("APRSReceiver", "Broadcast sent to MainActivity with position data")
        } else {
            Log.d("APRSReceiver", "Intent was null")
        }
    }
}
