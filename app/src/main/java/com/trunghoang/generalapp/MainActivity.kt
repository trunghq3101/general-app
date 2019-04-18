package com.trunghoang.generalapp

import android.content.*
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.support.annotation.RequiresApi
import android.support.v4.content.LocalBroadcastManager
import android.text.format.DateUtils
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.logging.SimpleFormatter

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    private var binder: IBinder? = null
    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "Service Disconnected")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "Service Connected")
            binder = service as MainService.LocalBinder
            bound = true
        }
    }
    private var bound = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonBindService.setOnClickListener {
            bindService(
                Intent(this, MainService::class.java),
                serviceConnection,
                Context.BIND_AUTO_CREATE
            )
        }
        buttonStartService.setOnClickListener {
            startForegroundService(Intent(this, MainService::class.java))
        }
        val filter = IntentFilter(MainService.ACTION_TIME)
        LocalBroadcastManager.getInstance(this).registerReceiver(
            object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    val time = intent?.getLongExtra(MainService.EXTRA_TIME, 0L) ?: 0L
                    updateTime(time)
                }
            },
            filter
        )
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Stop")
        if (bound) {
            unbindService(serviceConnection)
            bound = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, MainService::class.java))
    }

    fun updateTime(time: Long) {
        textTime.text = String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(time),
            TimeUnit.MILLISECONDS.toMinutes(time) -
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)),
            TimeUnit.MILLISECONDS.toSeconds(time) -
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
        );
    }
}
