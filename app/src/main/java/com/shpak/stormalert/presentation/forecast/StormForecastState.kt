package com.shpak.stormalert.presentation.forecast

import com.shpak.stormalert.domain.model.GeomagneticForecast

data class StormForecastState(
    val isLoading: Boolean = false,
    val forecast: GeomagneticForecast? = null,
    val isError: Boolean = false
)