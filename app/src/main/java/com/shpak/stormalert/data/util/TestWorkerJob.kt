package com.shpak.stormalert.data.util

import android.content.Context
import android.os.Build
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import java.util.Date

class TestWorkerJob(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationHelper.postNotification(
                applicationContext,
                title = "Test job",
                text = "It's ${Date()} o'clock"
            )
        }

        return Result.success()
    }
}