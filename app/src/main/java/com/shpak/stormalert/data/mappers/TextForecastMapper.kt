package com.shpak.stormalert.data.mappers

import com.shpak.stormalert.data.exception.MalformedStringException
import com.shpak.stormalert.domain.model.GeomagneticData
import com.shpak.stormalert.domain.model.GeomagneticForecast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.streams.toList

fun String.toGeomagneticForecast(): GeomagneticForecast {
    val dateRegex = "[A-Z][a-z]{2} *[0-9]{2}".toRegex()
    val datesRowRegex = "$dateRegex *$dateRegex *$dateRegex".toRegex()
    val timeRegex = "[0-9]{2}-[0-9]{2}UT".toRegex()
    val dataRowRegex = "$timeRegex.*".toRegex()
    val kpValueRegex = "[0-9]{1,2}\\.[0-9]+".toRegex()

    val dates = datesRowRegex.find(this)
        ?: throw MalformedStringException("Provided string does not contain forecast date")
    val allDataRows = dataRowRegex.findAll(this)

    val datesList = mutableListOf<Date>()

    dateRegex.findAll(dates.value).forEach {
        val dateFormat = SimpleDateFormat("MMM dd", Locale.US)
        val date = dateFormat.parse(it.value)?.setCurrentYear()
            ?: throw MalformedStringException("Provided string does not contain forecast date")

        datesList.add(date)
    }

    val forecastList = mutableListOf<GeomagneticData>()

    allDataRows.forEach {
        val forecastHours = timeRegex.find(it.value)
            ?: throw MalformedStringException("Provided string does not contain forecast time")
        val dateFormat = SimpleDateFormat("HH", Locale.US)
        val hoursDate = dateFormat.parse(forecastHours.value)
            ?: throw MalformedStringException("Provided string does not contain forecast time")

        val kpValues = kpValueRegex.findAll(it.value)
        if (kpValues.count() < 1) {
            throw MalformedStringException("Provided string does not contain forecast data")
        }
        kpValues.forEachIndexed { i, kpValue ->
            val time = datesList[i].setHours(hoursDate)
            forecastList.add(GeomagneticData(time, kpValue.value.toDouble()))
        }
    }

    return GeomagneticForecast(
        null,
        forecastList.stream().sorted { a, b -> a.date.compareTo(b.date) }.toList()
    )
}

fun Date.setCurrentYear(): Date {
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    calendar.time = this
    calendar.set(Calendar.YEAR, currentYear)
    return calendar.time
}

fun Date.setHours(date: Date): Date {
    val calendar = Calendar.getInstance()
    calendar.time = date
    val hours = calendar.get(Calendar.HOUR_OF_DAY)
    calendar.time = this
    calendar.set(Calendar.HOUR, hours)
    return calendar.time
}