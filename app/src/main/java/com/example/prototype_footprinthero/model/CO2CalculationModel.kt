package com.example.prototype_footprinthero.model

data class CO2CalculationModel(
    var co2: Float = 0f,
    val transportationCO2: Map<String, Float> = mapOf(
        "Auto" to 0.3f,
        "Fahrrad" to 0.0f,
        "Flugzeug" to 2.0f
    ),
    var selectedTransportation: String = "Auto",
    var duration: Int = 0
)

class CO2CalculationViewModel {
    val model = CO2CalculationModel()

    fun calculateCO2() {
        val co2Emission = model.transportationCO2[model.selectedTransportation] ?: 0f
        val calculatedCo2 = co2Emission * model.duration
        if (!calculatedCo2.isNaN()) {
            model.co2 = calculatedCo2
        }
    }
}
