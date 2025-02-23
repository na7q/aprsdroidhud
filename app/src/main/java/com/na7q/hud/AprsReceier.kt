package com.na7q.hud

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AprsReceiver(private val updateHud: (String, String, String, String, String, String, Int, Int, String) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AprsReceiver", "APRSdroid receiver triggered with intent: $intent")

        if (intent != null) {
            // Extract values from the intent
            val source = intent.getStringExtra("org.aprsdroid.app.SOURCE") ?: ""
            val callsign = intent.getStringExtra("org.aprsdroid.app.CALLSIGN") ?: ""
            val packet = intent.getStringExtra("org.aprsdroid.app.PACKET") ?: ""
            val symbol = intent.getStringExtra("org.aprsdroid.app.SYMBOL") ?: ""
            val comment = intent.getStringExtra("org.aprsdroid.app.COMMENT") ?: ""

            // Convert LAT and LON from microdegrees to degrees
            val latMicro = intent.getIntExtra("org.aprsdroid.app.LOCATION_LAT", 0)
            val lonMicro = intent.getIntExtra("org.aprsdroid.app.LOCATION_LON", 0)
            val latitude = latMicro / 1_000_000.0
            val longitude = lonMicro / 1_000_000.0
            val location = "Lat: $latitude, Lon: $longitude"

            val speed = intent.getIntExtra("org.aprsdroid.app.SPEED", 0)
            val course = intent.getIntExtra("org.aprsdroid.app.COURSE", 0)

            // Log the received data
            Log.d("AprsReceiver", "Received data: source=$source, location=$location, callsign=$callsign, packet=$packet")

            // Function to extract parsedTocall from the packet
            fun extractParsedTocall(packet: String): String? {
                val regex = ">([^,:]+)".toRegex()
                val matchResult = regex.find(packet)
                return matchResult?.groups?.get(1)?.value
            }

            // Extract parsedTocall from the packet
            val parsedTocall = extractParsedTocall(packet)

            val model = if (parsedTocall != null) {
                var result: String? = AprsPacket.processTocall(parsedTocall)

                if (result == null) {
                    result = AprsPacket.micetocall(comment.takeLast(2))
                }

                result ?: parsedTocall
            } else {
                null
            }

            // Pass data to the MainActivity for UI update
            updateHud(source, location, callsign, packet, comment, symbol, speed, course, model ?: "")
        }
    }
}
