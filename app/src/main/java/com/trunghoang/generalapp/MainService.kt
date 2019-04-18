package com.trunghoang.generalapp

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.*
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import java.util.concurrent.TimeUnit

class MainService : Service() {
    companion object {
        const val TAG = "MainService"
        const val ACTION_TIME = "com.trunghoang.generalapp.TIME"
        const val EXTRA_TIME = "Time"
    }

    private val binder = LocalBinder()
    private var builder: Notification.Builder? = null
    private var manager: NotificationManager? = null
    private var time: Long = 0L
    private var thread: ServiceHandlerThread? = null

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "onBind")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind")
        return super.onUnbind(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStart")
        startForeground(MainNotificationManager.NOTIFICATION_ID, getBuilder().build())
        if (thread == null) {
            thread = ServiceHandlerThread().apply {
                start()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
        thread?.isRunning = false
        getManager().cancel(MainNotificationManager.NOTIFICATION_ID)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getBuilder(): Notification.Builder = builder ?: run {
        MainNotificationManager.buildChannel(this)
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        builder = Notification.Builder(this, MainNotificationManager.CHANNEL_ID)
            .setContentTitle("General App")
            .setContentText("Counting time")
            .setSmallIcon(R.drawable.ic_audiotrack_black_24dp)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
        builder!!
    }

    fun getManager(): NotificationManager = manager ?: run {
        manager = ContextCompat.getSystemService(this, NotificationManager::class.java)
        manager!!
    }

    fun updateTime(time: Long) = String.format(
        "%02d:%02d:%02d",
        TimeUnit.MILLISECONDS.toHours(time),
        TimeUnit.MILLISECONDS.toMinutes(time) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)),
        TimeUnit.MILLISECONDS.toSeconds(time) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
    )

    inner class LocalBinder : Binder() {
        fun getService() = this@MainService
    }

    inner class ServiceHandlerThread: HandlerThread("ServiceHandlerThread") {
        var isRunning = true
        val mainHandler = Handler(mainLooper)
        @RequiresApi(Build.VERSION_CODES.O)
        override fun run() {
            while (isRunning) {
                Thread.sleep(1000)
                time += 1000
                val intent = Intent().apply {
                    action = ACTION_TIME
                    putExtra(EXTRA_TIME, time)
                }
                LocalBroadcastManager.getInstance(this@MainService).sendBroadcast(intent)
                mainHandler.post {
                    builder = getBuilder().setContentText(updateTime(time))
                    getManager().notify(
                        MainNotificationManager.NOTIFICATION_ID,
                        getBuilder().build()
                    )
                }
            }
            this.interrupt()
        }
    }
}
