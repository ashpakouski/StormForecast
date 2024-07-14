package com.shpak.stormalert.data.repository.remote

import com.shpak.stormalert.data.mappers.toGeomagneticForecast
import com.shpak.stormalert.data.network.TextDataSource
import com.shpak.stormalert.domain.model.GeomagneticForecast
import com.shpak.stormalert.domain.repository.GeomagneticForecastRepository
import com.shpak.stormalert.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultGeomagneticForecastRepository @Inject constructor(
    private val textDataSource: TextDataSource
) : GeomagneticForecastRepository {

    override suspend fun getGeomagneticForecast(): Resource<GeomagneticForecast> {
        return try {
            withContext(Dispatchers.Default) {
                Resource.Success(textDataSource.loadRawData().toGeomagneticForecast())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Unknown error")
        }
    }
}