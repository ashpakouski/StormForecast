package com.shpak.stormalert.presentation.map

import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.shpak.stormalert.R

@Composable
fun MapScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val bitmap = BitmapFactory.decodeResource(
            LocalContext.current.resources, R.drawable.world_location_map_optimized_raster_low
        )

        WorldMap(
            bitmap = bitmap,
            modifier = Modifier.fillMaxSize(),
            drawAbove = { scale, offset ->

            }
        )
    }
}