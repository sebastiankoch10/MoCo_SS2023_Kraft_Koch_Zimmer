package com.example.prototype_footprinthero.viewmodel

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlin.math.sqrt

class MotionDetectionService : Service(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var motionSensor: Sensor

    private var motionStartTimeMillis = 0L
    private var notificationShown = false

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        motionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
        startMotionDetection()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startMotionDetection()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // nur zum override
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onSensorChanged(event: SensorEvent?) {

        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            // Hier können Sie die Bewegungsdaten auswerten
            // und prüfen, ob die Bewegungsdauer 30 Minuten überschreitet
            // Beispiel:
            val motionDurationMinutes = calculateMotionDuration(x, y, z)

            if (motionDurationMinutes == 0 && notificationShown) {
                Toast.makeText(applicationContext, "Reset Time", Toast.LENGTH_SHORT).show()
                notificationShown = false
            }

            if (motionDurationMinutes == 30 && !notificationShown) {
                notificationShown = true
                Toast.makeText(applicationContext, "30 Minuten Bewegungsdauer erreicht", Toast.LENGTH_SHORT).show()
                //Log.d("MotionDetection", "30 Minuten Bewegungsdauer erreicht")
                // Hier können Sie den Log-Eintrag erstellen oder eine andere Aktion ausführen
                val notificationHelper = NotificationHelper(applicationContext)
                notificationHelper.showNotification()
            }
        }
    }



    private fun startMotionDetection() {
        sensorManager.registerListener(
            this,
            motionSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        motionStartTimeMillis = System.currentTimeMillis()
    }

    private fun stopMotionDetection() {
        sensorManager.unregisterListener(this)
        motionStartTimeMillis = 0L
    }

    private fun calculateMotionDuration(x: Float, y: Float, z: Float): Int {
        // Hier können Sie die Bewegungsdauer basierend auf den Sensordaten berechnen
        // Dies ist nur ein Beispiel, wie Sie die Dauer abschätzen könnten
        val acceleration = sqrt(x * x + y * y + z * z)
        val motionDurationMillis = System.currentTimeMillis() - motionStartTimeMillis
        return if (acceleration > 0.1f) {
            (motionDurationMillis / (1000 * 60)).toInt()
        } else {
            0
        }
    }
}

