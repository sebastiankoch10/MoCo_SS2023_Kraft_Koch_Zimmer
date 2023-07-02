package com.example.prototype_footprinthero.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import kotlin.random.Random

class NotificationHelper(private val context: Context) {

    private val CHANNEL_ID = "my_channel_id"

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = "Test Channel"
        val description = "This is a test channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            this.description = description
        }
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


    fun showNotification() {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:123456789"))
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Test")
            .setContentText("This is a test notification")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)

        if (notificationManager.areNotificationsEnabled()) {
            try {
                notificationManager.notify(Random.nextInt(), notification)
            } catch (e: SecurityException) {
                // Behandlung der SecurityException
                Toast.makeText(context, "Fehler beim Anzeigen der Benachrichtigung", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Benachrichtigungen sind deaktiviert
            Toast.makeText(context, "Bitte aktivieren Sie Benachrichtigungen", Toast.LENGTH_SHORT).show()
        }

    }


}
