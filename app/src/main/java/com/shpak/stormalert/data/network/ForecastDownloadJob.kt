package com.shpak.stormalert.data.network

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shpak.stormalert.domain.util.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class ForecastDownloadJob @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        NotificationHelper.postNotification(appContext)
        return Result.success()
    }
}