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
    private val typeText: TextView,
    private val distanceText: TextView,	
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
        qrg: String,
        type: String,
        distance: String
    ) {

		// If source and callsign are the same, hide sourceText
		if (source.isEmpty() || source == callsign) {
			sourceText.visibility = View.GONE
		} else {
			sourceText.text = source
			sourceText.visibility = View.VISIBLE
		}
		
		// Handle visibility of locationText and commentText based on their content
		if (location.isEmpty()) {
			locationText.visibility = View.GONE
		} else {
			locationText.text = location
			locationText.visibility = View.VISIBLE
		}
			
		callsignText.text = callsign
        packetText.text = packet

		// Handle visibility of commentText based on content
		if (comment.isEmpty()) {
			commentText.visibility = View.GONE
		} else {
			commentText.text = comment
			commentText.visibility = View.VISIBLE
		}

        symbolText.text = symbol
		symbolText.visibility = View.GONE		
		
		// Only display speed if it's greater than 0
		if (speed > 0) {
			speedText.text = "$speed mph"
			speedText.visibility = View.VISIBLE
		} else {
			speedText.visibility = View.GONE
		}
		
		// Only display course if it's greater than 0
		if (course > 0) {
			courseText.text = "$course°"
			courseText.visibility = View.VISIBLE
		} else {
			courseText.visibility = View.GONE
		}

        toCallText.text = toCall
        qrgText.text = qrg
        typeText.text = type

		// Handle visibility of commentText based on content
		if (distance.isEmpty()) {
			distanceText.visibility = View.GONE
		} else {
			distanceText.text = distance
			distanceText.visibility = View.VISIBLE
		}
		
		symbolView.setSymbol(symbol)		
		
		// Only update the symbol if it is not empty
		//if (symbol.isNotEmpty()) {
		//	symbolView.setSymbol(symbol)
		//} else {
		//	symbolView.setSymbol("") // Clear the symbol if none is provided
		//}			
    }

    // Method to update the current system time in the HUD
    fun updateCurrentTime(currentTime: String) {
        currentTimeText.text = "$currentTime"
    }

    // Method to update the last heard timestamp in the HUD
    fun updateTimestamp(formattedTime: String) {
        timestampText.text = "$formattedTime"
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
        outState.putString("type", typeText.text.toString())	
		outState.putString("distance", distanceText.text.toString())	

		// Save the visibility state of the views
		outState.putInt("sourceVisibility", sourceText.visibility)
		outState.putInt("locationVisibility", locationText.visibility)
		outState.putInt("commentVisibility", commentText.visibility)
		outState.putInt("symbolVisibility", symbolText.visibility)
		outState.putInt("speedVisibility", speedText.visibility)
		outState.putInt("courseVisibility", courseText.visibility)
		outState.putInt("toCallVisibility", toCallText.visibility)
		outState.putInt("qrgVisibility", qrgText.visibility)
		outState.putInt("typeVisibility", typeText.visibility)
		outState.putInt("distanceVisibility", distanceText.visibility)				
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
		typeText.text = savedInstanceState.getString("type", "")
		distanceText.text = savedInstanceState.getString("distance", "")

		// Restore the visibility state of the views
		sourceText.visibility = savedInstanceState.getInt("sourceVisibility", View.VISIBLE)
		locationText.visibility = savedInstanceState.getInt("locationVisibility", View.VISIBLE)
		commentText.visibility = savedInstanceState.getInt("commentVisibility", View.VISIBLE)
		symbolText.visibility = savedInstanceState.getInt("symbolVisibility", View.VISIBLE)
		speedText.visibility = savedInstanceState.getInt("speedVisibility", View.VISIBLE)
		courseText.visibility = savedInstanceState.getInt("courseVisibility", View.VISIBLE)
		toCallText.visibility = savedInstanceState.getInt("toCallVisibility", View.VISIBLE)
		qrgText.visibility = savedInstanceState.getInt("qrgVisibility", View.VISIBLE)
		typeText.visibility = savedInstanceState.getInt("typeVisibility", View.VISIBLE)
		distanceText.visibility = savedInstanceState.getInt("distanceVisibility", View.VISIBLE)

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