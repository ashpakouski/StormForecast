package com.shpak.stormalert.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shpak.stormalert.domain.model.GeomagneticData
import com.shpak.stormalert.presentation.util.formatTime

@Composable
fun ForecastCard(
    geomagneticData: GeomagneticData,
    isFirst: Boolean = false,
    isLast: Boolean = false
) {
    val defaultCornerRadius = 5.dp
    val maxCornerRadius = 12.dp

    val shape =
        if (isFirst && isLast) RoundedCornerShape(maxCornerRadius)
        else if (isFirst) RoundedCornerShape(
            maxCornerRadius,
            maxCornerRadius,
            defaultCornerRadius,
            defaultCornerRadius
        )
        else if (isLast) RoundedCornerShape(
            defaultCornerRadius,
            defaultCornerRadius,
            maxCornerRadius,
            maxCornerRadius
        )
        else RoundedCornerShape(defaultCornerRadius)

    Card(
        shape = shape,
        colors = CardDefaults.cardColors(),
        modifier = Modifier
            .padding(vertical = 2.dp)
            .fillMaxWidth()
    ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = geomagneticData.date.formatTime(),
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .weight(4.0F),
                    fontWeight = FontWeight(500),
                )
                Box(modifier = Modifier.weight(1.0F).alpha(0.75F)) {
                    StormStrengthIndicator(strength = geomagneticData.kpValue)
                }
                Text(
                    text = geomagneticData.kpValue.toString(),
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .weight(1.5F),
                    fontWeight = FontWeight(500),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}