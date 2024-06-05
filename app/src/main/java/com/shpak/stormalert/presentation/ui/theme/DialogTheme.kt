package com.shpak.stormalert.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    surface = Color(0xFF2D2E30),
    primary = Color(0xFFA6CAF7),
    onPrimary = Color(0xFF133251)
)

private val LightColorScheme = lightColorScheme(
    surface = Color(0xFFF3F5FA),
    primary = Color(0xFF3D6188),
    onPrimary = Color(0xFFFFFFFF)
)

@Composable
fun DialogTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}