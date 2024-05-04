package com.shpak.stormalert.domain.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import androidx.core.app.NotificationCompat
import com.shpak.stormalert.R
import java.util.Date

object NotificationHelper {

    fun postNotification(
        context: Context,
        channelId: String = "ID_DEFAULT",
        channelName: String = "All forecast notifications"
    ) {

        val notificationChannel = NotificationChannel(
            channelId, channelName, NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager = context.getSystemService(
            Service.NOTIFICATION_SERVICE
        ) as NotificationManager

        notificationManager.createNotificationChannel(notificationChannel)

        val notificationBuilder =
            NotificationCompat.Builder(context, channelId)
                .setContentTitle("Info")
                .setContentText("${Date()}")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setPriority(NotificationManager.IMPORTANCE_MIN)

        notificationManager.notify(1, notificationBuilder.build())
    }
}