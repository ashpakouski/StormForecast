package com.shpak.stormalert.domain.model

data class GeomagneticForecast(
    val kpMax24: Double?,               // Max Kp value observed in last 24 hours
    val forecast: List<GeomagneticData> // Kp value forecast
)