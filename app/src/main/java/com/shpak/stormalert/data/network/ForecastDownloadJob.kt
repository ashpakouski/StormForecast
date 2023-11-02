package com.shpak.stormalert.data.network

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.shpak.stormalert.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class ForecastDownloadJob @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        postNotification()
        return Result.success()
    }

    private fun postNotification() {
        val notificationChannel = NotificationChannel(
            "NOTIFICATION_CHANNEL_ID",
            "NOTIFICATION_CHANNEL_NAME",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val notificationManager =
            appContext.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)

        val notificationBuilder =
            NotificationCompat.Builder(appContext, "NOTIFICATION_CHANNEL_ID")
                .setContentTitle("Info")
                .setContentText("Millis: ${System.currentTimeMillis()}")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationManager.IMPORTANCE_MIN)

        notificationManager.notify(1, notificationBuilder.build())
    }
}