package com.shpak.stormalert.domain.repository

import com.shpak.stormalert.domain.model.GeomagneticForecast
import com.shpak.stormalert.domain.util.Resource

interface GeomagneticForecastRepository {
    suspend fun getGeomagneticForecast(): Resource<GeomagneticForecast>
}