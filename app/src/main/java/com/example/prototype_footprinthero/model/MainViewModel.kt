package com.example.prototype_footprinthero.model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class MainViewModel : ViewModel() {
    private val firestoreDatabase = FirestoreDatabase()

    val vehicles = listOf("Auto", "Fahrrad", "Flugzeug")
    private val co2CalculationViewModel = CO2CalculationViewModel()
    val selectedVehicle = mutableStateOf("Auto")
    var duration: Int = Integer.valueOf(1)
    private val co2 = mutableStateOf(0f)

    var co2DataList = ConsumptionDataList(mutableListOf())

    /* TODO Observer Pattern
    private val co2DataObserver = object : DataObserver {
        override fun onDataChangedFromObserver() {
            Log.e("MainViewModel", "onDataChangedFromObserver: ${co2DataList.co2Data}")
            updateWeekdayOverview()
        }
    }

    fun updateWeekdayOverview() {
        WeekdayOverview(co2DataList)
    }

    fun getCo2DataObserver(): DataObserver {
        return co2DataObserver
    }

     */


    init {
        //co2DataList.registerObserver(co2DataObserver)
        Log.i("MainViewModel", "init called")
        readDataInit("co2Data", "your_document_id")
    }

    fun onVehicleSelected(vehicle: String) {
        selectedVehicle.value = vehicle
        co2CalculationViewModel.onVehicleSelected(vehicle)
    }

    fun onDurationChanged(duration: Int) {
        co2CalculationViewModel.onDurationChanged(duration)
        //Log.d("MainViewModel", "Selected duration: $duration")
    }


    fun calculateCO2() {
        co2CalculationViewModel.calculateCO2()
        co2.value = co2CalculationViewModel.model.co2
        Log.d("MainViewModel", "calculate CO2: ${co2.value}")

        merchList(co2.value)
    }


    private fun merchList(value: Float) {
        val currentInstant = Clock.System.now()
        val localDateTime = currentInstant.toLocalDateTime(TimeZone.currentSystemDefault())
        val dayOfWeek = localDateTime.dayOfWeek
        val abbreviatedDayOfWeek = dayOfWeek.name.take(2)


        println("Heutiger Tag (abgekürzt): $abbreviatedDayOfWeek")

        val consumptionData = ConsumptionData(
            abbreviatedDayOfWeek, value
        )
        co2DataList.addConsumption(consumptionData)
        writeCO2Data(co2DataList)
    }

    private fun readDataInit(collectionName: String, documentId: String) {
        firestoreDatabase.readCO2Data(collectionName, documentId) { co2DataListDB, error ->
            if (co2DataListDB != null) {
                co2DataListDB.forEach { data ->
                    co2DataList.addConsumption(data)
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

    private fun writeCO2Data(co2DataList: ConsumptionDataList) {
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



