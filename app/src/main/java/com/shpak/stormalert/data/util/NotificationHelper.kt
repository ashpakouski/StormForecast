package com.shpak.stormalert.data.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.shpak.stormalert.R

object NotificationHelper {

    // TODO: Adapt method to work with older APIs
    @RequiresApi(Build.VERSION_CODES.O)
    fun postNotification(
        context: Context,
        channelId: String = "ID_DEFAULT",
        channelName: String = "All forecast notifications",
        title: String? = null,
        text: String? = null,
        actionIntent: Intent? = null
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
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .run {
                    if (actionIntent != null) {
                        val pendingIntent = PendingIntent.getActivity(
                            context, 0, actionIntent, PendingIntent.FLAG_IMMUTABLE
                        )
                        setContentIntent(pendingIntent)
                        setAutoCancel(true)
                    } else {
                        this
                    }
                }

        // TODO: Use another ID
        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }
}