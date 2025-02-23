package com.na7q.hud

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.Paint	
import android.util.AttributeSet
import android.widget.ImageView

class SymbolView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {

    private val iconBitmap: Bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.allicons) // Load the allicons.png image
    }

    // This method will be called to update the symbol image
    fun setSymbol(symbol: String) {
        val symbolBitmap = getSymbolBitmap(symbol) // Get the symbol image part from allicons.png
        setImageBitmap(symbolBitmap) // Update the ImageView with the symbol image
    }

    // Method to extract the correct symbol from the allicons.png image
    private fun getSymbolBitmap(symbol: String): Bitmap {
        val srcRect = symbol2rect(symbol) // Get the correct rectangular region for the symbol (base)
        val destRect = Rect(0, 0, width, height) // Adjust for the ImageView size

        // Create a new bitmap for the symbol
        val symbolBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(symbolBitmap)
        val drawPaint = Paint().apply {
            isAntiAlias = true
            isFilterBitmap = true
        }

        // Draw the base symbol bitmap
        canvas.drawBitmap(iconBitmap, srcRect, destRect, drawPaint)

        // Check if overlay is needed
        if (symbolIsOverlayed(symbol)) {
            // Get the overlay symbol (use page 2 for overlay)
            val overlaySymbolBitmap = getOverlaySymbolBitmap(symbol)
            val overlayDestRect = Rect(0, 0, width, height)
            canvas.drawBitmap(overlaySymbolBitmap, null, overlayDestRect, drawPaint) // Draw overlay on top of the base
        }

        return symbolBitmap
    }

    // This method calculates the rectangle based on the symbol
    private fun symbol2rect(symbol: String): Rect {
        val symbolSize = iconBitmap.width / 16 // Assuming 16 symbols per row
        val index = symbol[1].toInt() - 33 // ASCII value calculation (adjusted by 33)
        val page = if (symbol[0] == '/') 0 else 1 // Check if the symbol is on the first or second page
        return symbol2rect(index, page)
    }

    // Helper method to calculate the rect for the symbol
    private fun symbol2rect(index: Int, page: Int): Rect {
        val symbolSize = iconBitmap.width / 16
        val altOffset = page * symbolSize * 6
        val y = (index / 16) * symbolSize + altOffset
        val x = (index % 16) * symbolSize
        return Rect(x, y, x + symbolSize, y + symbolSize)
    }

    // This checks if the symbol requires an overlay (if the first character is not '/' or '\')
    private fun symbolIsOverlayed(symbol: String): Boolean {
        return symbol[0] != '/' && symbol[0] != '\\' // Overlay if the symbol doesn't start with '/' or '\'
    }

    // Helper method to get the overlay symbol bitmap (from page 2)
    private fun getOverlaySymbolBitmap(symbol: String): Bitmap {
        // Page 2 should be used for overlays
        val overlayPage = 2 // Page 2 is for overlays
        val overlayIndex = symbol[0].toInt() - 33 // ASCII calculation for overlay (from base)
        val overlaySymbolRect = symbol2rect(overlayIndex, overlayPage) // Get the rect for the overlay symbol
        val overlaySymbolBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(overlaySymbolBitmap)
        val drawPaint = Paint().apply {
            isAntiAlias = true
            isFilterBitmap = true
        }
        canvas.drawBitmap(iconBitmap, overlaySymbolRect, Rect(0, 0, width, height), drawPaint) // Draw the overlay symbol
        return overlaySymbolBitmap
    }
}
