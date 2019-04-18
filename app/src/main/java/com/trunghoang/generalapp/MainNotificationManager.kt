package com.trunghoang.generalapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class MainNotificationManager {
    companion object {
        const val CHANNEL_ID = "com.trunghoang.generalapp.CHANNEL_ID"
        const val NOTIFICATION_ID = 332

        @JvmStatic
        fun buildChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = "General App Notification"
                val descriptionText = "General App Notification"
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                    description = descriptionText
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}