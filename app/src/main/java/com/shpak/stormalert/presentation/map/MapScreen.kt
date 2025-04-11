package com.shpak.stormalert.presentation.map

import android.app.ActivityManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import com.shpak.stormalert.R
import kotlin.math.sqrt

@Composable
fun MapScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // val maxBitmapPixelCount = 150 * 1024 * 1024 / 4
        val drawable = ResourcesCompat.getDrawable(
            LocalContext.current.resources,
            R.drawable.world_location_map_optimized,
            null
        )
        val activityManager = LocalContext.current.getSystemService(ActivityManager::class.java)
        val memoryClassMB = activityManager.memoryClass
        val maxBitmapPixelCount = memoryClassMB * 1024 * 1024 / 8

        if (drawable != null) {
            val aspect = drawable.intrinsicWidth / drawable.intrinsicHeight
            val height = sqrt(maxBitmapPixelCount / aspect.toFloat())
            val width = height * aspect
            val bitmap = drawable.toBitmap(width.toInt(), height.toInt())

            PinchToZoomImage(
                bitmap = bitmap,
                scaleMax = 5f,
                modifier = Modifier.fillMaxSize(),
                drawAbove = { scale, offset ->
                }
            )
        }
    }
}