package com.na7q.hud

import android.widget.ImageView
import android.widget.TextView
import android.os.Bundle
import android.view.ViewTreeObserver
import android.view.View

class HudDisplay(
    private val sourceText: TextView,
    private val locationText: TextView,
    private val callsignText: TextView,
    private val packetText: TextView,
    private val timestampText: TextView,
    private val commentText: TextView,
    private val symbolText: TextView,
    private val speedText: TextView,
    private val courseText: TextView,
    private val toCallText: TextView,
    private val currentTimeText: TextView,
    private val qrgText: TextView,	
    private val symbolView: SymbolView // Add the SymbolView reference
) {

    // Method to update all TextViews and the symbol
    fun updateHud(
        source: String,
        location: String,
        callsign: String,
        packet: String,
        comment: String,
        symbol: String,
        speed: Int,
        course: Int,
        toCall: String,
        qrg: String		
    ) {
        sourceText.text = source
        locationText.text = location
        callsignText.text = callsign
        packetText.text = packet
        commentText.text = comment
        symbolText.text = symbol
		
		// Only display speed if it's greater than 0
		if (speed > 0) {
			speedText.text = "Speed: $speed mph"
			speedText.visibility = View.VISIBLE
		} else {
			speedText.visibility = View.GONE
		}
		
		// Only display course if it's greater than 0
		if (course > 0) {
			courseText.text = "Course: $course°"
			courseText.visibility = View.VISIBLE
		} else {
			courseText.visibility = View.GONE
		}

        toCallText.text = toCall
        qrgText.text = qrg
        
        // Update the symbol image in SymbolView
        symbolView.setSymbol(symbol) // This calls the setSymbol method of SymbolView
    }

    // Method to update the current system time in the HUD
    fun updateCurrentTime(currentTime: String) {
        currentTimeText.text = "Time: $currentTime"
    }

    // Method to update the last heard timestamp in the HUD
    fun updateTimestamp(formattedTime: String) {
        timestampText.text = "Last Heard: $formattedTime"
    }

    // Save state method
    fun saveInstanceState(outState: Bundle) {
        outState.putString("source", sourceText.text.toString())
        outState.putString("location", locationText.text.toString())
        outState.putString("callsign", callsignText.text.toString())
        outState.putString("packet", packetText.text.toString())
        outState.putString("comment", commentText.text.toString())
        outState.putString("symbol", symbolText.text.toString())
        outState.putString("speed", speedText.text.toString())
        outState.putString("course", courseText.text.toString())
        outState.putString("toCall", toCallText.text.toString())
        outState.putString("qrg", qrgText.text.toString())		
    }

	fun restoreInstanceState(savedInstanceState: Bundle) {
		sourceText.text = savedInstanceState.getString("source", "")
		locationText.text = savedInstanceState.getString("location", "")
		callsignText.text = savedInstanceState.getString("callsign", "")
		packetText.text = savedInstanceState.getString("packet", "")
		commentText.text = savedInstanceState.getString("comment", "")
		symbolText.text = savedInstanceState.getString("symbol", "")
		speedText.text = savedInstanceState.getString("speed", "")
		courseText.text = savedInstanceState.getString("course", "")
		toCallText.text = savedInstanceState.getString("toCall", "")
		qrgText.text = savedInstanceState.getString("qrg", "")

		// Retrieve the symbol from savedInstanceState
		val savedSymbol = savedInstanceState.getString("symbol", "")

		// Add OnPreDrawListener to ensure SymbolView is measured
		symbolView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
			override fun onPreDraw(): Boolean {
				// Remove the listener once the layout pass is complete
				symbolView.viewTreeObserver.removeOnPreDrawListener(this)

				// Now we can safely update the symbol
				if (!savedSymbol.isNullOrEmpty()) {
					symbolView.setSymbol(savedSymbol)
				}
				return true // Continue with drawing the rest of the view
			}
		})
	}

}