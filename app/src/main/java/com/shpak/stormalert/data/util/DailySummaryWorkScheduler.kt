package com.shpak.stormalert.data.util

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DailySummaryWorkScheduler @Inject constructor(
    context: Context
) {
    private val workManager = WorkManager.getInstance(context)

    fun scheduleJob(
        job: Class<out ListenableWorker> = DailySummaryJob::class.java,
        jobId: String = "job_id_daily_summary"
    ) {
        val targetHour = 21
        val targetMinute = 50
        val targetOffsetSeconds = targetHour * 60 * 60 + targetMinute * 60

        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        val currentSecond = calendar.get(Calendar.SECOND)
        val currentOffsetSeconds = currentHour * 60 * 60 + currentMinute * 60 + currentSecond

        val initialDelaySeconds = targetOffsetSeconds - currentOffsetSeconds +
                if (targetOffsetSeconds > currentOffsetSeconds) 0 else 24 * 60 * 60

        val workRequest = PeriodicWorkRequest.Builder(
            job,
            6L,
            TimeUnit.HOURS
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).setInitialDelay(initialDelaySeconds.toLong(), TimeUnit.SECONDS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            jobId,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            workRequest
        )
    }
}