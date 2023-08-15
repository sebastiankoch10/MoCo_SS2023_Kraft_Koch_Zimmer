package com.example.prototype_footprinthero.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.example.prototype_footprinthero.ui.theme.Prototype_FootPrintHeroTheme
import com.example.prototype_footprinthero.view.Login
import com.example.prototype_footprinthero.view.MainScreen
import com.example.prototype_footprinthero.viewmodel.MotionDetectionService

class MainActivity : AppCompatActivity() {
    private var isLoggedIn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Prototype_FootPrintHeroTheme {
                Surface(color = Color.White) {
                    if (isLoggedIn) {
                        MainScreen(isLoggedIn = isLoggedIn, onLogout = { isLoggedIn = false })
                    } else {
                        Login(onLoginSuccess = { isLoggedIn = true })
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startMotionDetectionService()
    }

    override fun onPause() {
        super.onPause()
        stopMotionDetectionService()
    }

    private fun startMotionDetectionService() {
        val intent = Intent(this, MotionDetectionService::class.java)
        startService(intent)
    }

    private fun stopMotionDetectionService() {
        val intent = Intent(this, MotionDetectionService::class.java)
        stopService(intent)
    }
}
