package com.example.prototype_footprinthero.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.prototype_footprinthero.ui.theme.Prototype_FootPrintHeroTheme
import com.example.prototype_footprinthero.view.LoginScreen
import com.example.prototype_footprinthero.view.MainScreen
import com.example.prototype_footprinthero.viewmodel.MainViewModel
import com.example.prototype_footprinthero.viewmodel.MotionDetectionService

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var motionDetectionService: MotionDetectionService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        motionDetectionService = MotionDetectionService()

        setContent {
            Prototype_FootPrintHeroTheme {

                val isLoggedIn = viewModel.isLoggedIn

                if (isLoggedIn) {
                    MainScreen(viewModel = viewModel)
                } else {
                    LoginScreen(viewModel = viewModel, onLoginClicked = { username, password ->
                        val isValid = viewModel.performLogin(username, password)
                        if (isValid) {
                            // Variable isLoggedIn wird automatisch im ViewModel aktualisiert
                        }
                    })
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
