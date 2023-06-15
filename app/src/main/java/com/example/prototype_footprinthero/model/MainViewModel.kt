package com.example.prototype_footprinthero.model

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf

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
