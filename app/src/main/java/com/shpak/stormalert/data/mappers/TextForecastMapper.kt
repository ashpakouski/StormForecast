package com.shpak.stormalert.data.mappers

import com.shpak.stormalert.data.exception.MalformedStringException
import com.shpak.stormalert.domain.model.GeomagneticData
import com.shpak.stormalert.domain.model.GeomagneticForecast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.streams.toList

private val dateRegex = "[A-Z][a-z]{2} *[0-9]{2}".toRegex()
private val datesRowRegex = "$dateRegex *$dateRegex *$dateRegex".toRegex()
private val timeRegex = "[0-9]{2}-[0-9]{2}UT".toRegex()
private val dataRowRegex = "$timeRegex.*".toRegex()
private val kpValueRegex = "[0-9]{1,2}\\.[0-9]+".toRegex()

fun String.toGeomagneticForecast(): GeomagneticForecast {
    val forecastDates = retrieveForecastDates()
    val forecastList = retrieveForecastData(forecastDates)

    return GeomagneticForecast(
        null,
        forecastList.stream().map { gmd -> GeomagneticData(gmd.date.toLocalTime(), gmd.kpValue) }
            .sorted { gmd1, gmd2 -> gmd1.date.compareTo(gmd2.date) }.toList()
    )
}

/**
 * This method is used to parse forecast dates from a raw text response.
 * Dates are appended to the current year.
 *
 * ...
 * NOAA Kp index breakdown Sep 08-Sep 10 2023
 *              Sep 08       Sep 09       Sep 10         <--- DATES
 * 00-03UT       2.67         3.00         2.67
 * ...
 */
private fun String.retrieveForecastDates(): List<Date> {
    val dateStringFormat = "MMM dd"

    val datesRow = datesRowRegex.find(this)
        ?: throw MalformedStringException("Provided string does not contain forecast date")

    return mutableListOf<Date>().also { datesList ->
        dateRegex.findAll(datesRow.value).forEach {
            val dateFormat = SimpleDateFormat(dateStringFormat, Locale.US)
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = dateFormat.parse(it.value)?.setCurrentYear()
                ?: throw MalformedStringException("Provided string does not contain forecast date")

            datesList.add(date)
        }
    }
}

/**
 * This method is used to parse forecast values from a raw text response.
 * Every forecast entry is returned with corresponding date and time.
 *
 * ...
 * NOAA Kp index breakdown Sep 08-Sep 10 2023
 *              Sep 08       Sep 09       Sep 10
 * 00-03UT       2.67         3.00         2.67          <--- FORECAST
 * ...
 */
private fun String.retrieveForecastData(forecastDates: List<Date>): List<GeomagneticData> {
    val hoursStringFormat = "HH"
    val allDataRows = dataRowRegex.findAll(this)

    return mutableListOf<GeomagneticData>().also { forecastList ->
        allDataRows.forEach {
            val forecastHours = timeRegex.find(it.value)
                ?: throw MalformedStringException("Provided string does not contain forecast time")
            val dateFormat = SimpleDateFormat(hoursStringFormat, Locale.US)
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val hoursDate = dateFormat.parse(forecastHours.value)
                ?: throw MalformedStringException("Provided string does not contain forecast time")

            val kpValues = kpValueRegex.findAll(it.value)
            if (kpValues.count() < 1) {
                throw MalformedStringException("Provided string does not contain forecast data")
            }
            kpValues.forEachIndexed { i, kpValue ->
                val time = forecastDates[i].copyHours(hoursDate)
                forecastList.add(GeomagneticData(time, kpValue.value.toDouble()))
            }
        }
    }
}

fun Date.toLocalTime(): Date {
    val localTime = Date(System.currentTimeMillis())
    return Date(this.time + TimeZone.getDefault().getOffset(localTime.time))
}

fun Date.setCurrentYear(): Date {
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    calendar.time = this
    calendar.set(Calendar.YEAR, currentYear)
    return calendar.time
}

fun Date.copyHours(date: Date): Date {
    val calendar = Calendar.getInstance()
    calendar.time = date
    val hours = calendar.get(Calendar.HOUR_OF_DAY)
    calendar.time = this
    calendar.set(Calendar.HOUR, hours)
    return calendar.time
}