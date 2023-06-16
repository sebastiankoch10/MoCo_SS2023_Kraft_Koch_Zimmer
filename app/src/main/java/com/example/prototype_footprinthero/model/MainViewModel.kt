package com.example.prototype_footprinthero.model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val vehicles = listOf("Auto", "Fahrrad", "Flugzeug")
    val co2CalculationViewModel = CO2CalculationViewModel()

    val selectedVehicle = mutableStateOf("Auto")
    val duration = mutableStateOf(0)
    val co2 = mutableStateOf(0f)

    fun onVehicleSelected(vehicle: String) {
        selectedVehicle.value = vehicle
        //co2CalculationViewModel.selectedTransportation = vehicle
    }

    fun onDurationChanged(duration: Int) {
        this.duration.value = duration
        //co2CalculationViewModel.duration = duration
    }

    fun calculateCO2() {
        co2CalculationViewModel.calculateCO2()
        co2.value = co2CalculationViewModel.model.co2
    }
}


