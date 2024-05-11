package com.shpak.stormalert.domain.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import androidx.core.app.NotificationCompat
import com.shpak.stormalert.R

/**
 * Notifications:
 * Storm Ahead
 * Receive notifications when a geomagnetic storm with a high Kp index is forecasted to occur in the near future
 * Sudden Increase
 * Get notified when the current Kp index shows a significant and unexpected increase compared to recent values
 * Daily Forecast
 * Receive a daily summary of the expected magnetic activity level for the following day
 */

object NotificationHelper {

    fun postNotification(
        context: Context,
        channelId: String = "ID_DEFAULT",
        channelName: String = "All forecast notifications",
        title: String? = null,
        text: String? = null
    ) {

        val notificationChannel = NotificationChannel(
            channelId, channelName, NotificationManager.IMPORTANCE_MIN
        )

        val notificationManager = context.getSystemService(
            Service.NOTIFICATION_SERVICE
        ) as NotificationManager

        notificationManager.createNotificationChannel(notificationChannel)

        val notificationBuilder =
            NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationManager.IMPORTANCE_MIN)

        notificationManager.notify(1, notificationBuilder.build())
    }
}