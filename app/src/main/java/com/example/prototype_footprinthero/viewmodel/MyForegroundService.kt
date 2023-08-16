package com.example.prototype_footprinthero.viewmodel

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder

@Suppress("DEPRECATION")
class MyForegroundService : Service() {

    companion object {
        private const val SERVICE_NOTIFICATION_ID = 1
    }

    private var notification: Notification? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            notification = intent.getParcelableExtra("notification")
            if (notification != null) {
                startForeground(SERVICE_NOTIFICATION_ID, notification)
            }
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
    }
}

