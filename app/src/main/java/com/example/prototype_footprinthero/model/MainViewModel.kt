package com.example.prototype_footprinthero.model

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.prototype_footprinthero.observers.DataObserver
import com.example.prototype_footprinthero.view.WeekdayOverview
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


    val co2DataList = mutableStateOf(ConsumptionDataList(mutableListOf()))

    private val co2DataObserver = object : DataObserver {
        override fun onDataChangedFromObserver() {
            // Die geänderte co2DataList an das MutableState-Objekt übergeben
            co2DataList.value = co2DataList.value
        }
    }

    fun getCo2DataObserver(): DataObserver {
        return co2DataObserver
    }


    init {
        co2DataList.value.registerObserver(co2DataObserver)
        readDataInit("co2Data", "your_document_id")
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
            abbreviatedDayOfWeek, value
        )
        co2DataList.value.addConsumption(consumptionData)
        writeCO2Data(co2DataList.value)
    }

    fun readDataInit(collectionName: String, documentId: String) {
        firestoreDatabase.readCO2Data(collectionName, documentId) { co2DataListDB, error ->
            if (co2DataListDB != null) {
                co2DataListDB.forEach { data ->
                    co2DataList.value.addConsumption(data)
                }
                co2DataListDB.forEach { data ->
                    val dayOfWeek = data.dayOfWeek
                    val co2 = data.value

                    Log.d(
                        "MainViewModel", "Read From DB, CO2 data: Tag: $dayOfWeek, CO2: $co2"
                    )
                }
            } else {
                Log.e("MainViewModel", "Failed to read CO2 data: $error")
            }
        }
    }

    fun writeCO2Data(co2DataList: ConsumptionDataList) {
        val collectionName = "co2Data"
        val documentId = "your_document_id"

        firestoreDatabase.writeCO2Data(
            co2DataList, collectionName, documentId
        ) { success, error ->
            if (success) {
                co2DataList.co2Data.forEach { data ->
                    val dayOfWeek = data.dayOfWeek
                    val co2 = data.value

                    Log.d(
                        "MainViewModel", "Write to DB CO2 data: Tag: $dayOfWeek, CO2: $co2"
                    )
                }

            } else {
                Log.e("MainViewModel", "Failed to write CO2 data: $error")
            }
        }
    }
}



