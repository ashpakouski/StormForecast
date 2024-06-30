package com.shpak.stormalert.data.util

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
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
        initialDelayMillis: Long
    ) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequest.Builder(job)
            .setInitialDelay(initialDelayMillis, TimeUnit.MILLISECONDS)
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(
            jobId, ExistingWorkPolicy.REPLACE, workRequest
        )
    }

    override fun cancel(jobId: String) {
        workManager.cancelUniqueWork(jobId)
    }
}