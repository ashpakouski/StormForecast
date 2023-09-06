package com.shpak.stormalert.domain.repository

import com.shpak.stormalert.domain.model.GeomagneticForecast
import com.shpak.stormalert.domain.util.Resource

interface GeomagneticRepository {
    suspend fun getGeomagneticForecast(): Resource<GeomagneticForecast>
}