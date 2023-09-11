package com.shpak.stormalert.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shpak.stormalert.domain.model.GeomagneticData
import com.shpak.stormalert.presentation.util.formatDay
import java.util.Calendar

@Composable
fun GroupedForecast(forecast: List<GeomagneticData>) {
    val dateGroups = forecast.groupBy {
        val calendar = Calendar.getInstance()
        calendar.time = it.date
        calendar.get(Calendar.DATE)
    }

    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        dateGroups.values.forEach { forecastGroup ->
            item {
                Text(
                    text = forecastGroup.first().date.formatDay(),
                    modifier = Modifier.padding(top = 12.dp, bottom = 4.dp),
                    fontWeight = FontWeight(550)
                )
            }
            itemsIndexed(forecastGroup) { i, gmd ->
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