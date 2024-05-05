package com.shpak.stormalert.presentation.forecast

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shpak.stormalert.domain.model.GeomagneticData
import com.shpak.stormalert.presentation.util.formatDay
import java.util.Calendar

fun LazyListScope.groupedForecast(forecast: List<GeomagneticData>) {
    val dateGroups = forecast.groupBy {
        val calendar = Calendar.getInstance()
        calendar.time = it.date
        calendar.get(Calendar.DATE)
    }

    dateGroups.values.forEach { forecastGroup ->
        item {
            Text(
                text = forecastGroup.first().date.formatDay(),
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp),
                fontWeight = FontWeight(550)
            )
        }

        forecastGroup.forEachIndexed { i, gmd ->
            item {
                ForecastCard(
                    GeomagneticData(
                        date = gmd.date,
                        kpValue = gmd.kpValue
                    ),
                    isFirst = i == 0,
                    isLast = i == forecastGroup.size - 1
                )
            }
        }
    }
}