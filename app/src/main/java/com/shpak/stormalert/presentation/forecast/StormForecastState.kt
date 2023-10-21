package com.shpak.stormalert.presentation.forecast

import com.shpak.stormalert.domain.model.GeomagneticForecast

data class StormForecastState(
    var isLoading: Boolean = false,
    var forecast: GeomagneticForecast? = null,
    var isError: Boolean = false
)