package com.na7q.hud

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AprsReceiver(private val updateHud: (String, String, String, String, String, String, Int, Int, String, String, String) -> Unit) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
		Log.d("AprsReceiver", "APRS receiver triggered with intent: ${intent?.action}")

        if (intent != null) {

			// Determine the source of the intent (APRSdroid or NA7Q)
			val prefix = when (intent.action) {
				"org.aprsdroid.app.HUD" -> "org.aprsdroid.app."
				"org.na7q.app.HUD" -> "org.na7q.app."
				else -> return  // Ignore unknown intents
			}
			
            // Extract values from the intent			
            val packet = intent.getStringExtra("${prefix}PACKET") ?: ""
			val type = AprsPacket.getPacketType(packet)
			
            var source = intent.getStringExtra("${prefix}SOURCE") ?: "" //Objects Source Callsign
            var callsign = intent.getStringExtra("${prefix}CALLSIGN") ?: "" //Station Callsign
            var symbol = intent.getStringExtra("${prefix}SYMBOL") ?: ""
            var comment = intent.getStringExtra("${prefix}COMMENT") ?: ""

			// Convert LAT and LON from microdegrees to degrees only if they are nonzero
			val latMicro = intent.getIntExtra("${prefix}LOCATION_LAT", 0)
			val lonMicro = intent.getIntExtra("${prefix}LOCATION_LON", 0)

			val location = if (latMicro != 0 && lonMicro != 0) {
				val latitude = latMicro / 1_000_000.0
				val longitude = lonMicro / 1_000_000.0
				String.format("%.4f, %.4f", latitude, longitude)
			} else {
				"" // Leave location empty if lat/lon are missing
			}

            val speed = intent.getIntExtra("${prefix}SPEED", 0)
            val course = intent.getIntExtra("${prefix}COURSE", 0)
			val qrg = intent.getStringExtra("${prefix}QRG")?.takeIf { it.isNotEmpty() }?.let { "$it" + "MHz" } ?: ""

			// If callsign is empty, try extracting it from the packet
			if (callsign.isEmpty()) {
				callsign = AprsPacket.getSource(packet) ?: ""  // Extract callsign from packet
			}

			// Modify values based on packet type (if not Position or Object)
			if (type == "Status") {
				comment = AprsPacket.handleStatus(packet) 
				symbol = "S&"  // Set a default symbol for "Status" type
			} else if (type == "Telemetry") {
				comment = AprsPacket.handleTelemetry(packet) 
				symbol = "T&"  // Set a default symbol for "Telemetry" type		
			} else if (type == "Other") {
				comment = AprsPacket.handleTelemetry(packet)  //Add other once cleaned up
				symbol = "\\&"  // Set a default symbol for "Other" type		
			}


            // Log the received data
            Log.d("AprsReceiver", "Received data: source=$source, location=$location, callsign=$callsign, packet=$packet, qrg=$qrg, type=$type")

            // Function to extract parsedTocall from the packet
            fun extractParsedTocall(packet: String): String? {
                val regex = ">([^,:]+)".toRegex()
                val matchResult = regex.find(packet)
                return matchResult?.groups?.get(1)?.value
            }

            // Extract parsedTocall from the packet
            val parsedTocall = extractParsedTocall(packet)

			val model = if (parsedTocall != null) {
				AprsPacket.processTocall(parsedTocall)
					?: AprsPacket.micetocall(comment.takeLast(2))
					?: AprsPacket.kenwoodtocall(comment.takeLast(1))
					?: AprsPacket.oldkenwoodtocall(packet)
					?: parsedTocall
			} else {
				null
			}

			comment = AprsPacket.parseComment(comment)

            // Pass data to the MainActivity for UI update
            updateHud(source, location, callsign, packet, comment, symbol, speed, course, model ?: "", qrg, type)
        }
    }
}
