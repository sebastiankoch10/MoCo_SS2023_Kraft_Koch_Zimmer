package com.example.prototype_footprinthero.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _co2DataList = MutableLiveData<ConsumptionDataList>()
    val co2DataList: LiveData<ConsumptionDataList> get() = _co2DataList


    var dayInGerman = ""
    var calendarWeek = 0


    init {
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
        val updatedList = _co2DataList.value?.apply {
            addConsumption(consumptionData)
        } ?: ConsumptionDataList(mutableListOf(consumptionData))

        _co2DataList.value = updatedList
        writeCO2Data(updatedList)

        // Benachrichtigen Sie die Beobachter (Observer) über die Änderung der co2DataList
        _co2DataList.postValue(updatedList) //TODO nur wenn erfolgreich geaddet wurde
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

    fun readDataInit(collectionName: String, documentId: String) {
        Log.i("MainViewModel", "readDataInit called")
        firestoreDatabase.readCO2Data(collectionName, documentId) { co2DataListDB, error ->
            if (co2DataListDB != null) {
                _co2DataList.value?.let { co2DataList ->
                    co2DataListDB.forEach { data ->
                        co2DataList.addConsumption(data)
                        _co2DataList.postValue(co2DataList)

                        val dayOfWeek = data.dayOfWeek
                        val co2 = data.co2
                        val calendarWeek = data.calendarWeek

                        Log.d(
                            "MainViewModel",
                            "Read From DB, co2 data: Tag: $dayOfWeek, CO2: $co2, CalendarWeek: $calendarWeek"
                        )
                    }

                    val finalCo2DataList = co2DataList // Separate Referenz
                    Log.d("MainViewModel", "co2DataList size after adding: ${finalCo2DataList.co2Data.size}")
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