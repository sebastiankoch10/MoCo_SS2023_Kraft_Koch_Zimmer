package com.example.prototype_footprinthero.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.prototype_footprinthero.model.ConsumptionData
import com.example.prototype_footprinthero.model.ConsumptionDataList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.Locale

class MainViewModel : ViewModel() {
    private val firestoreDatabase = FirestoreDatabase()

    var selectedMonth: Int by mutableStateOf(0)
    var isLoggedIn: Boolean by mutableStateOf(false)
        private set

    fun performLogin(username: String, password: String): Boolean {
        Log.d("MainViewModel.performLogin", "performLogin called with Username: $username, Password: $password")
        val isValidCredentials = checkCredentials(username, password)
        isLoggedIn = isValidCredentials
        return isValidCredentials
    }


    private fun checkCredentials(username: String, password: String): Boolean {

        val validUsername = "abc"
        val validPassword = "def"

        return username == validUsername && password == validPassword
    }


    val vehicles = listOf("Auto","Bahn","Bus",
        //"Fahrrad","zu Fuß",
        "Taxi","Flugzeug")
    private val co2CalculationViewModel = CO2CalculationViewModel()
    val selectedVehicle = mutableStateOf("Auto")
    var duration: Int = Integer.valueOf(1)
    private val co2 = mutableStateOf(0f)

    //Observe Data
    private val _co2DataList = MutableStateFlow(ConsumptionDataList(mutableListOf()))
    val co2DataList: StateFlow<ConsumptionDataList> get() = _co2DataList

    var dayInGerman = ""
    var calendarWeek = 0


    init {
        Log.i("MainViewModel.init", "init called")
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
        Log.d("MainViewModel.calculate", "calculate CO2: ${co2.value}")

        merchList(co2.value)
    }


    private fun merchList(co2: Float) {
        val abbreviatedDayOfWeek = dayInGerman

        Log.i("MainViewModel.merchList", "Heutiger Tag (abgekürzt): $abbreviatedDayOfWeek")

        val consumptionData = ConsumptionData(
            abbreviatedDayOfWeek, co2, getCurrentCalendarWeek()
        )

        val updatedList = _co2DataList.value.co2Data.toMutableList()
        updatedList.add(consumptionData)
        _co2DataList.value = ConsumptionDataList(updatedList)

        writeCO2Data(_co2DataList.value)

        Log.d("MainViewModel.merchList", "Observer benachrichtigt: ${_co2DataList.value}")
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
            "MainViewModel.getCurrentCalendarWeek",
            "Current Calendar Week: ${currentDate.get(weekFields.weekOfWeekBasedYear())}"
        )
        return currentDate.get(weekFields.weekOfWeekBasedYear())
    }

    fun readDataInit(collectionName: String, documentId: String) {
        Log.i("MainViewModel.readDataInit", "readDataInit called")
        firestoreDatabase.readCO2Data(collectionName, documentId) { co2DataListDB, error ->
            if (error != null) {
                Log.e("MainViewModel.readDataInit", "Failed to read co2 data: $error")
                // Führen Sie hier die entsprechenden Fehlerbehandlungsmaßnahmen durch
            } else {
                if (co2DataListDB != null) {
                    val currentList = _co2DataList.value.co2Data.toMutableList()
                    currentList.addAll(co2DataListDB)

                    val updatedList = ConsumptionDataList(currentList)
                    _co2DataList.value = updatedList

                    // Weitere Operationen mit der aktualisierten Liste durchführen
                    // ...

                    Log.d(
                        "MainViewModel.readDataInit",
                        "_co2DataList size after adding: ${updatedList.co2Data.size}"
                    )

                    // Ausgabe der gelesenen Elemente
                    co2DataListDB.forEachIndexed { index, data ->
                        Log.i("MainViewModel.readDataInit", "Gelesenes Element $index: $data")
                    }
                } else {
                    Log.e("MainViewModel.readDataInit", "Failed to read co2 data: Empty data")
                    // Führen Sie hier die entsprechenden Fehlerbehandlungsmaßnahmen durch
                }
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
                        "MainViewModel .writeCO2Data",
                        "Write to DB CO2 data: Tag: $dayOfWeek, CO2: $co2, CalendarWeek: $calendarWeek"
                    )
                }
            } else {
                Log.e("MainViewModel.writeCO2Data", "Failed to write CO2 data: $error")
            }
        }
    }
}