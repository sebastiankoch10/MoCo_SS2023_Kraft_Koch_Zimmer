package com.example.prototype_footprinthero.model

import android.util.Log
import com.example.prototype_footprinthero.viewmodel.MainViewModel
import java.util.Calendar
import kotlin.random.Random


data class ConsumptionDataList(val co2Data: MutableList<ConsumptionData>) {




    fun createTestData(): ConsumptionDataList {
        val daysOfWeek = listOf("mo", "di", "mi", "do", "fr", "sa", "so")
        val testDataList = mutableListOf<ConsumptionData>()

        for (day in daysOfWeek) {
            val randomValue = Random.nextFloat() * 10f // Random value between 0 and 10
            val randomWeekOfDay = Random.nextInt(1, 53) // Random value between 1 and 52
            testDataList.add(ConsumptionData(day, randomValue, randomWeekOfDay))
        }

        return ConsumptionDataList(testDataList)
    }

    fun size(): Int {
        return co2Data.size
    }


    fun addConsumption(consumptionData: ConsumptionData) {
        co2Data.add(consumptionData)
        Log.d("ConsumptionDataList", "addConsumption: ${consumptionData.co2}")
    }


    fun aggregateToDaysOfThisWeek(viewModel: MainViewModel): ConsumptionDataList {
        val aggregatedList = ConsumptionDataList(mutableListOf())

        Log.i("aggregateByDayOfWeek", "aggregateByDayOfWeek called addConsumptions")
        co2Data.groupBy { it.dayOfWeek }.forEach { (_, dataList) ->
            val firstData = dataList.firstOrNull { it.calendarWeek == viewModel.calendarWeek }
            if (firstData != null) {
                val totalValue = dataList.filter { it.calendarWeek == viewModel.calendarWeek }
                    .sumOf { it.co2.toDouble() }.toFloat()

                val aggregatedData =
                    ConsumptionData(firstData.dayOfWeek, totalValue, firstData.calendarWeek)

                aggregatedList.addConsumption(aggregatedData)
            }
        }

        return aggregatedList

    }

    fun aggregateWeeksOfMonths(viewModel: MainViewModel): ConsumptionDataList {
        val aggregatedList = ConsumptionDataList(mutableListOf())

        val calendar = Calendar.getInstance()
        val currentWeek = calendar.get(Calendar.WEEK_OF_YEAR)

        val weeksToInclude = listOf(currentWeek - 3, currentWeek - 2, currentWeek - 1, currentWeek)

        weeksToInclude.forEach { week ->
            val weekData = co2Data.filter { it.calendarWeek == week }
            if (weekData.isNotEmpty()) {
                val totalValue = weekData.sumOf { it.co2.toDouble() }.toFloat()
                val aggregatedData = ConsumptionData("Woche $week", totalValue, week)
                aggregatedList.addConsumption(aggregatedData)
            }
        }

        return aggregatedList
    }



    fun aggregateMonthOfHalfYear(viewModel: MainViewModel): ConsumptionDataList {
        val aggregatedList = ConsumptionDataList(mutableListOf())

        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH)

        val monthToInclude = listOf(currentMonth - 5, currentMonth - 4, currentMonth - 3, currentMonth - 2, currentMonth - 1, currentMonth)

        monthToInclude.forEach { month ->
            val weekData = co2Data.filter { it.calendarWeek == month }
            if (weekData.isNotEmpty()) {
                val totalValue = weekData.sumOf { it.co2.toDouble() }.toFloat()
                val aggregatedData = ConsumptionData("Monat $month", totalValue, month)
                aggregatedList.addConsumption(aggregatedData)
            }
        }

        return aggregatedList
    }








    fun map(): List<Map<String, Any>> {
        return co2Data.map { barData ->
            mapOf(
                "dayOfWeek" to barData.dayOfWeek, "Co2" to barData.co2
            )
        }
    }

    fun maxByOrNull(selector: (ConsumptionData) -> Float): ConsumptionData? {
        return co2Data.maxByOrNull(selector)
    }


    fun forEach(action: (ConsumptionData) -> Unit) {
        co2Data.forEach(action)
    }

    fun find(predicate: (ConsumptionData) -> Boolean): ConsumptionData? {
        return co2Data.find(predicate)
    }
}

data class ConsumptionData(
    val dayOfWeek: String,
    val co2: Float,
    val calendarWeek: Int,
    val monthNumber: Int,
    val weekNumber: Int
) {
    constructor(
        dayOfWeek: String,
        co2: Float,
        calendarWeek: Int
    ) : this(dayOfWeek, co2, calendarWeek, 0, 0)
}
