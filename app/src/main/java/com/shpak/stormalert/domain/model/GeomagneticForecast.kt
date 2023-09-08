package com.shpak.stormalert.domain.model

data class GeomagneticForecast(
    val kpMax: Double?,
    val forecast: List<GeomagneticData>
)