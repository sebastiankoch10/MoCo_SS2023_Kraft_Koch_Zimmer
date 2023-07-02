package com.example.prototype_footprinthero.viewmodel

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.prototype_footprinthero.R
import kotlin.math.sqrt

class MotionDetectionService : Service(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var motionSensor: Sensor

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        motionSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)!!
        startMotionDetection()
    }

    fun startService() {
        startMotionDetection()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startMotionDetection()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMotionDetection()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Nicht benötigt
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            // Hier können Sie die Bewegungsdaten auswerten
            // und prüfen, ob die Bewegungsdauer 30 Minuten überschreitet
            // Beispiel:
            val motionDurationMinutes = calculateMotionDuration(event.values[0], event.values[1], event.values[2])
            if (motionDurationMinutes >= 30) {
                Log.d("MotionDetection", "Bewegungsdauer: $motionDurationMinutes Minuten")
                // Hier können Sie den Log-Eintrag erstellen oder eine andere Aktion ausführen
            }
        }
    }

    private fun startMotionDetection() {
        sensorManager.registerListener(this, motionSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    private fun stopMotionDetection() {
        sensorManager.unregisterListener(this)
    }

    private fun calculateMotionDuration(x: Float, y: Float, z: Float): Int {
        // Hier können Sie die Bewegungsdauer basierend auf den Sensordaten berechnen
        // Dies ist nur ein Beispiel, wie Sie die Dauer abschätzen könnten
        val acceleration = sqrt((x * x + y * y + z * z).toDouble())
        val motionDurationMillis = (acceleration * 1000).toLong()
        return (motionDurationMillis / (1000 * 60)).toInt()
    }
}
