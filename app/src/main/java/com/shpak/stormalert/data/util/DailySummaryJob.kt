package com.shpak.stormalert.data.util

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shpak.stormalert.domain.repository.GeomagneticRepository
import com.shpak.stormalert.domain.util.Resource
import com.shpak.stormalert.presentation.MainActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.Calendar
import java.util.Date

@HiltWorker
class DailySummaryJob @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val forecastRepository: GeomagneticRepository,
    private val jobScheduler: JobScheduler
) : CoroutineWorker(appContext, params) {

    companion object {
        private const val JOB_ID = "job_id.daily_summary"

        private val scheduleTo = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 20)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
        }

        fun schedule(jobScheduler: JobScheduler) {
            val currentTime = Calendar.getInstance()

            if (currentTime.after(scheduleTo)) {
                scheduleTo.add(Calendar.DAY_OF_YEAR, 1)
            }

            val initialDelayMillis = scheduleTo.timeInMillis - currentTime.timeInMillis

            jobScheduler.schedule(
                DailySummaryJob::class.java, JOB_ID, initialDelayMillis
            )
        }

        fun cancel(jobScheduler: JobScheduler) {
            jobScheduler.cancel(JOB_ID)
        }
    }

    override suspend fun doWork(): Result {
        when (
            val forecast = forecastRepository.getGeomagneticForecast()
        ) {
            is Resource.Success -> {
                val geomagneticData = forecast.data?.forecast ?: return Result.retry()

                val now = Calendar.getInstance()

                val tomorrowForecast = geomagneticData.filter {
                    val futureCalendar = Calendar.getInstance()
                    futureCalendar.time = it.date

                    futureCalendar.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR) + 1 ||
                            futureCalendar.get(Calendar.YEAR) == now.get(Calendar.YEAR) + 1
                }

                val tomorrowMin = tomorrowForecast.minBy { it.kpValue }.kpValue
                val tomorrowMax = tomorrowForecast.maxBy { it.kpValue }.kpValue

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationHelper.postNotification(
                        applicationContext,
                        title = "$tomorrowMin / $tomorrowMax Kp tomorrow",
                        text = "Now: ${Date()}",
                        actionIntent = Intent(applicationContext, MainActivity::class.java)
                    )
                }

                schedule(jobScheduler)

                return Result.success()
            }

            is Resource.Error -> return Result.retry()
        }
    }
}