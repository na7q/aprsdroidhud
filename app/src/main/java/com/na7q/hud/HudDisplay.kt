package com.na7q.hud

import android.widget.TextView
import android.os.Bundle

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
    private val currentTimeText: TextView
) {

    // Method to update all TextViews based on received data
    fun updateHud(
        source: String,
        location: String,
        callsign: String,
        packet: String,
        comment: String,
        symbol: String,
        speed: Int,
        course: Int,
        toCall: String
    ) {
        sourceText.text = source
        locationText.text = location
        callsignText.text = callsign
        packetText.text = packet
        commentText.text = comment
        symbolText.text = symbol
        speedText.text = "Speed: $speed mph"
        courseText.text = "Course: $course°"
        toCallText.text = toCall
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
    }

    // Restore state method
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
    }
}
