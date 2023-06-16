package com.example.prototype_footprinthero

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.prototype_footprinthero.model.MainViewModel
import com.example.prototype_footprinthero.ui.theme.Prototype_FootPrintHeroTheme
import com.example.prototype_footprinthero.view.MainScreen

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Prototype_FootPrintHeroTheme {
                Surface(color = Color.White) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        Prototype_FootPrintHeroTheme {
            MainScreen( viewModel = viewModel)
        }
    }
}
