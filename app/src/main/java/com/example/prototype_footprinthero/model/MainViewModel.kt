package com.example.prototype_footprinthero.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class MainViewModel : ViewModel() {
    val firestoreDatabase = FirestoreDatabase()

    val vehicles = listOf("Auto", "Fahrrad", "Flugzeug")
    val co2CalculationViewModel = CO2CalculationViewModel()

    val selectedVehicle = mutableStateOf("Auto")
    var duration: Int = Integer.valueOf(1)
    val co2 = mutableStateOf(0f)
    val co2DataDB = mutableStateOf(listOf<ConsumptionData>())

    init {
        readData("co2Data", "your_document_id")
    }


    fun readData(collectionName: String, documentId: String) {
        firestoreDatabase.readCO2Data(collectionName, documentId) { co2DataList, error ->
            if (co2DataList != null) {
                co2DataDB.value = co2DataList
                Log.d("MainViewModel", "CO2 data read successfully ${co2DataList.joinToString()}")
            } else {
                Log.e("MainViewModel", "Failed to read CO2 data: $error")
            }
        }
    }

    fun onVehicleSelected(vehicle: String) {
        selectedVehicle.value = vehicle
        co2CalculationViewModel.onVehicleSelected(vehicle)
    }

    fun onDurationChanged(duration: Int) {
        co2CalculationViewModel.onDurationChanged(duration)
        Log.d("MainViewModel", "Selected duration: $duration")
    }

    fun calculateCO2() {
        co2CalculationViewModel.calculateCO2()
        co2.value = co2CalculationViewModel.model.co2
        Log.d("MainViewModel", "calculate CO2: ${co2.value}")

        merchList(co2.value)
    }

    fun merchList(value: Float) {
        val currentInstant = Clock.System.now()
        val localDateTime = currentInstant.toLocalDateTime(TimeZone.currentSystemDefault())
        val dayOfWeek = localDateTime.dayOfWeek
        val abbreviatedDayOfWeek = dayOfWeek.name.take(2)

        // Verwenden Sie abbreviatedDayOfWeek in Ihrer Logik oder tun Sie, was auch immer Sie damit machen möchten
        println("Heutiger Tag (abgekürzt): $abbreviatedDayOfWeek")

        val consumptionData = ConsumptionData(
            abbreviatedDayOfWeek,
            value
        )
        writeCO2Data(consumptionData)
    }

    fun writeCO2Data(consumptionData: ConsumptionData) {
        val collectionName = "co2Data"
        val documentId = "your_document_id"

        val firestoreDatabase = FirestoreDatabase() // Instanz von FirestoreDatabase erstellen

        firestoreDatabase.writeCO2Data(
            listOf(consumptionData),
            collectionName,
            documentId
        ) { success, error ->
            if (success) {
                Log.d(
                    "MainViewModel",
                    "CO2 data written successfully ${consumptionData.dayOfWeek} ${consumptionData.value}"
                )
            } else {
                Log.e("MainViewModel", "Failed to write CO2 data: $error")
            }
        }
    }
}



