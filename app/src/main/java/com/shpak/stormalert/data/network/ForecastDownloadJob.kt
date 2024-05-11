package com.shpak.stormalert.data.network

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shpak.stormalert.domain.repository.GeomagneticRepository
import com.shpak.stormalert.domain.util.NotificationHelper
import com.shpak.stormalert.domain.util.Resource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.Date

@HiltWorker
class ForecastDownloadJob @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    private val geomagneticRepository: GeomagneticRepository
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        when (
            val forecast = geomagneticRepository.getGeomagneticForecast()
        ) {
            is Resource.Success -> {
                val kpIn24Hours = forecast.data?.forecast?.firstOrNull {
                    it.date.after(Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                }
                val title = "Kp ${kpIn24Hours?.kpValue} in 24 hours"
                val text = "Kp in 24 hours: ${kpIn24Hours?.kpValue} (${kpIn24Hours?.date})"

                NotificationHelper.postNotification(appContext, title = title, text = text)
            }

            is Resource.Error -> {
                NotificationHelper.postNotification(
                    appContext,
                    title = "Error",
                    text = "Couldn't obtain Kp value"
                )
            }
        }

        return Result.success()
    }
}