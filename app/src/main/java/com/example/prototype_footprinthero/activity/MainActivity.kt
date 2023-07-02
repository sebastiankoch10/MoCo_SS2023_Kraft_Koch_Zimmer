package com.example.prototype_footprinthero.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.Color
import com.example.prototype_footprinthero.ui.theme.Prototype_FootPrintHeroTheme
import com.example.prototype_footprinthero.view.MainScreen
import com.example.prototype_footprinthero.viewmodel.MainViewModel
import com.example.prototype_footprinthero.viewmodel.MotionDetectionService

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var motionDetectionService: MotionDetectionService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        motionDetectionService = MotionDetectionService()

        setContent {
            Prototype_FootPrintHeroTheme {
                Surface(color = Color.White) {
                    MainScreen(
                        viewModel = viewModel
                    )
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
        motionDetectionService.startService()
    }

    private fun stopMotionDetectionService() {
        motionDetectionService.stopService()
    }
}









