package com.shpak.stormalert.presentation.map

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.min

@Composable
fun PinchToZoomImage(
    bitmap: Bitmap,
    modifier: Modifier = Modifier,
    scaleMin: Float = 1.0f,
    scaleMax: Float = 3.0f,
    drawAbove: DrawScope.() -> Unit = { }
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    BoxWithConstraints(
        modifier = modifier
    ) {
        val scaleImplicit = min(
            constraints.maxWidth / bitmap.width.toFloat(),
            constraints.maxHeight / bitmap.height.toFloat()
        )

        val imageSizeInitial = Size(
            bitmap.width.toFloat() * scaleImplicit,
            bitmap.height.toFloat() * scaleImplicit
        )

        val state = rememberTransformableState { zoomChange, panChange, _ ->
            scale = (scale * zoomChange).coerceIn(scaleMin, scaleMax)

            val extraWidth = (imageSizeInitial.width * scale - constraints.maxWidth)
                .coerceAtLeast(0f)
            val extraHeight = (imageSizeInitial.height * scale - constraints.maxHeight)
                .coerceAtLeast(0f)

            val maxX = extraWidth / 2f
            val maxY = extraHeight / 2f

            offset = Offset(
                x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY),
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clipToBounds()
        ) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    }
                    .transformable(state)
                    .align(Alignment.Center)
                    .drawWithContent {
                        drawContent()
                        drawAbove()
                    }
            )
        }
    }
}
