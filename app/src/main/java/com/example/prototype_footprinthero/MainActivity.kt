package com.example.prototype_footprinthero

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prototype_footprinthero.ui.theme.Prototype_FootPrintHeroTheme

class MainViewModel : ViewModel() {
    val vehicles = listOf("Auto", "Fahrrad", "Flugzeug")
    val selectedVehicle = mutableStateOf("Auto")
    val duration = mutableStateOf(0)
    val co2 = mutableStateOf(0f)

    fun onVehicleSelected(vehicle: String) {
        selectedVehicle.value = vehicle
    }

    fun onDurationChanged(duration: Int) {
        this.duration.value = duration
    }

    fun calculateCO2() {
        val transportationCO2 = mapOf("Auto" to 0.3f, "Fahrrad" to 0.0f, "Flugzeug" to 2.0f)
        val co2Emission = transportationCO2[selectedVehicle.value] ?: 0f
        val calculatedCo2 = co2Emission * duration.value
        if (!calculatedCo2.isNaN()) {
            co2.value = calculatedCo2
        }
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Prototype_FootPrintHeroTheme {
                Surface(color = Color.White) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Footprint Hero",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(16.dp)
        )

        DropdownMenu(
            expanded = false,
            onDismissRequest = { /* Empty */ }
        ) {
            viewModel.vehicles.forEach { vehicle ->
                DropdownMenuItem(onClick = {
                    viewModel.onVehicleSelected(vehicle)
                }) {
                    Text(text = vehicle)
                }
            }
        }

        OutlinedTextField(
            value = viewModel.duration.value.toString(),
            onValueChange = { newValue ->
                viewModel.onDurationChanged(newValue.toIntOrNull() ?: 0)
            },
            label = { Text(text = "Duration") }
        )

        Button(
            onClick = { viewModel.calculateCO2() },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Calculate CO2")
            Text(text = "Calculate CO2")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "CO2: ${viewModel.co2.value} kg",
            style = MaterialTheme.typography.h6
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Prototype_FootPrintHeroTheme {
        MainScreen()
    }
}
