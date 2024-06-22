package com.shpak.stormalert.data.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.shpak.stormalert.R

object NotificationHelper {

    @RequiresApi(Build.VERSION_CODES.O)
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
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setPriority(NotificationManager.IMPORTANCE_MIN)

        notificationManager.notify(1, notificationBuilder.build())
    }
}