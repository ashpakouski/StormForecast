package com.shpak.stormalert.data.util

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NetworkRequestJobScheduler @Inject constructor(
    context: Context
) : JobScheduler {

    private val workManager = WorkManager.getInstance(context)

    override fun schedule(
        job: Class<out ListenableWorker>,
        jobId: String,
        repeatIntervalMillis: Long,
        initialDelayMillis: Long
    ) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequest.Builder(
            job, repeatIntervalMillis, TimeUnit.MILLISECONDS
        ).setInitialDelay(initialDelayMillis, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniquePeriodicWork(
            jobId, ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, workRequest
        )
    }
}