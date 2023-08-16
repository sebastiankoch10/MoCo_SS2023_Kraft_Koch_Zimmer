package com.example.prototype_footprinthero.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class NotificationHelper(private val context: Context) {

    private val CHANNEL_ID = "my_channel_id"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = "Footprint Hero calculation"
        val description = "You do a journy, use the Footrint Hero calulation"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            this.description = description
        }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotification() {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Footprint Hero calculation")
            .setContentText("You do a journy, use the Footrint Hero calulation")
            .setPriority(NotificationCompat.PRIORITY_HIGH).setContentIntent(pendingIntent)
            .setAutoCancel(true).build()

        val notificationManager = NotificationManagerCompat.from(context)

        if (notificationManager.areNotificationsEnabled()) {
            try {
                val serviceIntent = Intent(context, MyForegroundService::class.java)
                serviceIntent.putExtra("notification", notification)
                ContextCompat.startForegroundService(context, serviceIntent)
            } catch (e: SecurityException) {
                // Behandlung der SecurityException
                Toast.makeText(
                    context, "Fehler beim Anzeigen der Benachrichtigung", Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            // Benachrichtigungen sind deaktiviert
            Toast.makeText(context, "Bitte aktivieren Sie Benachrichtigungen", Toast.LENGTH_SHORT)
                .show()
        }
    }
}


