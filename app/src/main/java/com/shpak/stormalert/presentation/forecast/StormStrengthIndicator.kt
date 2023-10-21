package com.shpak.stormalert.presentation.forecast

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StormStrengthIndicator(strength: Double) {
    val color = when {
        strength < 4.0 -> Color(0xFF1FFC50)
        strength >= 4 && strength < 5 -> Color(0xFFFFE700)
        strength >= 5 && strength < 6 -> Color(0xFFFF801E)
        strength >= 6 && strength < 7 -> Color(0xFFFE4512)
        else -> Color(0xFFFF0004)
    }

    Canvas(modifier = Modifier.size(24.dp), onDraw = {
        drawCircle(color = color)
    })
}