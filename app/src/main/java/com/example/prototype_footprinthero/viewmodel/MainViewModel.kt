package com.example.prototype_footprinthero.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.prototype_footprinthero.model.ConsumptionData
import com.example.prototype_footprinthero.model.ConsumptionDataList
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

class MainViewModel : ViewModel() {
    private val firestoreDatabase = FirestoreDatabase()

    val vehicles = listOf("Auto", "Fahrrad", "Flugzeug")
    private val co2CalculationViewModel = CO2CalculationViewModel()
    val selectedVehicle = mutableStateOf("Auto")
    var duration: Int = Integer.valueOf(1)
    private val co2 = mutableStateOf(0f)

    var co2DataList = ConsumptionDataList(mutableListOf())
    var dayInGerman = ""
    var calendarWeek = 0


    init {
        //co2DataList.registerObserver(co2DataObserver)
        Log.i("MainViewModel", "init called")
        readDataInit("co2Data", "your_document_id")
        dayInGerman = weekdayInGerman()
        calendarWeek = getCurrentCalendarWeek()
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


    private fun merchList(co2: Float) {


        val abbreviatedDayOfWeek = dayInGerman


        Log.i("MainViewModel", "Heutiger Tag (abgekürzt): $abbreviatedDayOfWeek")

        val consumptionData = abbreviatedDayOfWeek.let {
            ConsumptionData(
                it, co2, getCurrentCalendarWeek()
            )
        }
        co2DataList.addConsumption(consumptionData)
        writeCO2Data(co2DataList)
    }

    private fun weekdayInGerman(): String {
        val currentDateTime = LocalDateTime.now()
        val dayOfWeek = currentDateTime.dayOfWeek
        return dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.GERMANY)
    }

    fun getCurrentCalendarWeek(): Int {
        val currentDate = LocalDate.now()
        val weekFields = WeekFields.of(Locale.getDefault())
        Log.d(
            "MainViewModel_getCurrentCalendarWeek",
            "Current Calendar Week: ${currentDate.get(weekFields.weekOfWeekBasedYear())}"
        )
        return currentDate.get(weekFields.weekOfWeekBasedYear())
    }

    private fun readDataInit(collectionName: String, documentId: String) {
        firestoreDatabase.readCO2Data(collectionName, documentId) { co2DataListDB, error ->
            if (co2DataListDB != null) {
                co2DataListDB.forEach { data ->
                    co2DataList.addConsumption(data)
                }
                co2DataListDB.forEach { data ->
                    val dayOfWeek = data.dayOfWeek
                    val co2 = data.co2
                    val calendarWeek = data.calendarWeek

                    Log.d(
                        "MainViewModel",
                        "Read From DB, co2 data: Tag: $dayOfWeek, CO2: $co2, CalendarWeek: $calendarWeek"
                    )
                }
            } else {
                Log.e("MainViewModel", "Failed to read co2 data: $error")
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
                    val co2 = data.co2
                    val calendarWeek = data.calendarWeek

                    Log.d(
                        "MainViewModel",
                        "Write to DB CO2 data: Tag: $dayOfWeek, CO2: $co2, CalendarWeek: $calendarWeek"
                    )
                }

            } else {
                Log.e("MainViewModel", "Failed to write CO2 data: $error")
            }
        }
    }
}



