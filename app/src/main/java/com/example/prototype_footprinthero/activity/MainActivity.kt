package com.example.prototype_footprinthero.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.Color
import com.example.prototype_footprinthero.ui.theme.Prototype_FootPrintHeroTheme
import com.example.prototype_footprinthero.view.MainScreen
import com.example.prototype_footprinthero.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Prototype_FootPrintHeroTheme {
                Surface(color = Color.White) {
                    MainScreen(
                        viewModel = viewModel
                    )
                }
            }
        }

        // Observe the co2DataList State
        viewModel.co2DataList.observe(this) { co2DataList ->
            Log.i("MainActivity", "co2DataList changed: $co2DataList")
            // Rufen Sie die Update-Funktion auf
            updateWeekdayOverview()
        }
    }

    private fun updateWeekdayOverview() {
        // Aktualisieren Sie nur den betroffenen Teil der View
        Log.i("MainActivity", "updateWeekdayOverview called")
        viewModel.refreshWeekdayOverview()
    }
}






