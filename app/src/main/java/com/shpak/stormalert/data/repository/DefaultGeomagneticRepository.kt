package com.shpak.stormalert.data.repository

import com.shpak.stormalert.data.mappers.toGeomagneticForecast
import com.shpak.stormalert.data.network.TextDataSource
import com.shpak.stormalert.domain.model.GeomagneticForecast
import com.shpak.stormalert.domain.repository.GeomagneticRepository
import com.shpak.stormalert.domain.util.Resource
import javax.inject.Inject

class DefaultGeomagneticRepository @Inject constructor(
    private val textDataSource: TextDataSource
) : GeomagneticRepository {
    override suspend fun getGeomagneticForecast(): Resource<GeomagneticForecast> {
        return try {
            Resource.Success(textDataSource.loadRawData().toGeomagneticForecast())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}