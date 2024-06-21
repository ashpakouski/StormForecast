package com.shpak.stormalert.data.util

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
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

        val targetHour = 22
        val targetMinute = 47
        val targetOffsetMinutes = targetHour * 60 + targetMinute

        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        val currentOffsetMinutes = currentHour * 60 + currentMinute

        val initialDelayMinutes = targetOffsetMinutes - currentOffsetMinutes +
                if (targetOffsetMinutes > currentOffsetMinutes) 0 else 24 * 60

        val workRequest = PeriodicWorkRequest.Builder(
            testJobClass,
            1L,
            TimeUnit.DAYS
        ).setConstraints(
            Constraints.Builder()
                // .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )//.setInitialDelay(initialDelayMinutes.toLong(), TimeUnit.MINUTES)
            .build()

        workManager.enqueueUniquePeriodicWork(
            testJobId,
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            workRequest
        )
    }
}