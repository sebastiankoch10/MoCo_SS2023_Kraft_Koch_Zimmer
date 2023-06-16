package com.example.prototype_footprinthero.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val vehicles = listOf("Auto", "Fahrrad", "Flugzeug")
    val co2CalculationViewModel = CO2CalculationViewModel()

    val selectedVehicle = mutableStateOf("Auto")
    var duration = mutableStateOf(0f)
    val co2 = mutableStateOf(0f)

    fun onVehicleSelected(vehicle: String) {
        selectedVehicle.value = vehicle
        co2CalculationViewModel.onVehicleSelected(vehicle)
    }

    fun onDurationChanged(duration: Float) {
        co2CalculationViewModel.onDurationChanged(duration)
        Log.d("MainViewModel", "Selected duration: $duration")
    }

    fun calculateCO2() {
        co2CalculationViewModel.calculateCO2()
        co2.value = co2CalculationViewModel.model.co2
        Log.d("MainViewModel", "calculate CO2: ${co2.value}")
    }
}


