package com.shpak.stormalert.data.network

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ForecastJobScheduler @Inject constructor(private val application: Application) {
    companion object {
        private const val FORECAST_JOB_ID = "forecast_job_id"
    }

    fun scheduleJob() {
        val workRequest = PeriodicWorkRequest.Builder(
            ForecastDownloadJob::class.java,
            1L,
            TimeUnit.DAYS
        ).setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).build()

        WorkManager.getInstance(application)
            .enqueueUniquePeriodicWork(
                FORECAST_JOB_ID,
                ExistingPeriodicWorkPolicy.KEEP, workRequest
            )
    }

    fun clearJob() {
        WorkManager.getInstance(application).cancelUniqueWork(FORECAST_JOB_ID)
    }
}