package com.example.prototype_footprinthero.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore


class MainViewModel : ViewModel() {
    val firestoreDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()

    val vehicles = listOf("Auto", "Fahrrad", "Flugzeug")
    val co2CalculationViewModel = CO2CalculationViewModel()

    val selectedVehicle = mutableStateOf("Auto")
    var duration = Integer.valueOf(0)
    val co2 = mutableStateOf(0f)

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
    }

    fun writeCO2Data(barData: BarData) {
        val collectionName = "co2Data"
        val documentId = "your_document_id"

        val firestoreDatabase = FirestoreDatabase() // Instanz von FirestoreDatabase erstellen

        firestoreDatabase.writeCO2Data(
            listOf(barData),
            collectionName,
            documentId
        ) { success, error ->
            if (success) {
                Log.d("MainViewModel", "CO2 data written successfully")
            } else {
                Log.e("MainViewModel", "Failed to write CO2 data: $error")
            }
        }
    }
}



