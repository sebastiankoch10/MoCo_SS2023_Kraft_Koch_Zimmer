package com.example.prototype_footprinthero.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.ui.graphics.Color
import com.example.prototype_footprinthero.R
import com.example.prototype_footprinthero.ui.theme.Prototype_FootPrintHeroTheme
import com.example.prototype_footprinthero.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Observe the co2DataList LiveData
        viewModel.co2DataList.observe(this) { co2DataList ->
            // Aktualisieren Sie Ihre View mit den neuen Daten
            // Hier können Sie Ihre Logik zur Aktualisierung der View einfügen
        }

        setContent {
            Prototype_FootPrintHeroTheme {
                Surface(color = Color.White) {
                    MainScreen(
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}




