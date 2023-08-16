package com.example.prototype_footprinthero.viewmodel

import android.util.Log

data class CO2CalculationModel(
    var co2: Float = 0f, val transportationCO2: Map<String, Float> = mapOf(
        "Auto" to 0.162f, "Fahrrad" to 0.0f, "Flugzeug" to 0.25f
    ), var selectedTransportation: String = "Auto", var duration: Int = 0
)


class CO2CalculationViewModel {
    val model = CO2CalculationModel()


    fun calculateCO2() {
        val co2Emission = model.transportationCO2[model.selectedTransportation] ?: 0f
        val calculatedCo2 = (co2Emission * model.duration) / 60f
        if (!calculatedCo2.isNaN()) {
            model.co2 = calculatedCo2
        }
        Log.d("CO2CalculationModel", "Calculated CO2: $calculatedCo2")
    }

    fun onVehicleSelected(vehicle: String) {
        model.selectedTransportation = vehicle
        Log.d("CO2CalculationViewModel", "Selected vehicle: $vehicle")
    }

    fun onDurationChanged(duration: Int) {
        model.duration = duration
        Log.d("CO2CalculationViewModel", "Selected duration: $duration")
    }
}


