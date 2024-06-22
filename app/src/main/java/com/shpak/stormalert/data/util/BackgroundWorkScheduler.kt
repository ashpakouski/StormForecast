package com.shpak.stormalert.data.util

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class BackgroundWorkScheduler @Inject constructor(
    context: Context
) {
    private val workManager = WorkManager.getInstance(context)

    fun scheduleJob() {
        val testJobClass = TestWorkerJob::class.java
        val testJobId = "test_job_id"

        val targetHour = 20
        val targetMinute = 0
        val targetOffsetSeconds = targetHour * 60 * 60 + targetMinute * 60

        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        val currentSecond = calendar.get(Calendar.SECOND)
        val currentOffsetSeconds = currentHour * 60 * 60 + currentMinute * 60 + currentSecond

        val initialDelaySeconds = targetOffsetSeconds - currentOffsetSeconds +
                if (targetOffsetSeconds > currentOffsetSeconds) 0 else 24 * 60 * 60

        val workRequest = PeriodicWorkRequest.Builder(
            testJobClass,
            1L,
            TimeUnit.DAYS
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).setInitialDelay(initialDelaySeconds.toLong(), TimeUnit.SECONDS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            testJobId,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            workRequest
        )
    }
}