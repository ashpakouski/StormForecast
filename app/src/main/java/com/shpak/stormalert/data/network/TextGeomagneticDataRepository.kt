package com.shpak.stormalert.data.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class TextGeomagneticDataRepository : TextDataSource {

    override suspend fun loadRawData(): String {
        return withContext(Dispatchers.IO) {
            URL("https://services.swpc.noaa.gov/text/3-day-forecast.txt").readText()
        }
    }
}