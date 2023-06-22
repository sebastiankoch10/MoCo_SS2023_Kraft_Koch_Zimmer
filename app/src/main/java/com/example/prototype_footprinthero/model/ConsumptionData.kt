package com.example.prototype_footprinthero.model

import android.util.Log
import kotlin.random.Random


data class ConsumptionDataList(val co2Data: MutableList<ConsumptionData>) {

    /* TODO Observer Pattern
    private val observers: MutableList<DataObserver> = mutableListOf()

    fun registerObserver(observer: DataObserver) {
        Log.d("ConsumptionDataList", "new registerObserver")
        observers.add(observer)
    }

    fun unregisterObserver(observer: DataObserver) {
        observers.remove(observer)
    }


    private fun notifyObservers() {
        observers.forEach { observer ->
            observer.onDataChangedFromObserver()
            Log.d("ConsumptionDataList", "notifyObservers: onDataChangedFromObserver called")
        }
    }

     */

    fun createTestData(): ConsumptionDataList {
        val daysOfWeek = listOf("mo", "di", "mi", "do", "fr", "sa", "so")
        val testDataList = mutableListOf<ConsumptionData>()

        for (day in daysOfWeek) {
            val randomValue = Random.nextFloat() * 10f // Random value between 0 and 10
            val randomWeekOfDay = Random.nextInt(1, 53) // Random value between 1 and 52
            testDataList.add(ConsumptionData(day, randomValue,randomWeekOfDay))
        }

        return ConsumptionDataList(testDataList)
    }

    fun size(): Int {
        return co2Data.size
    }


    fun addConsumption(consumptionData: ConsumptionData) {
        co2Data.add(consumptionData)
        Log.d("ConsumptionDataList", "addConsumption: ${consumptionData.Co2}")
        //notifyObservers() TODO Observer Pattern
    }

    fun aggregateByDayOfWeek(): ConsumptionDataList {
        Log.i("aggregateByDayOfWeek", "aggregateByDayOfWeek called")
        val aggregatedList = ConsumptionDataList(mutableListOf())

        co2Data.groupBy { it.dayOfWeek }.forEach { (_, dataList) ->
            Log.d("aggregateByDayOfWeek", "Co2DataListenlänge: ${dataList.size}")
            val totalValue = dataList.sumOf { it.Co2.toDouble() }.toFloat()
            val firstData = dataList.first()
            val aggregatedData = ConsumptionData(firstData.dayOfWeek, totalValue, firstData.weekOfYear)
            aggregatedList.addConsumption(aggregatedData)
            Log.d(
                "aggregateByDayOfWeek",
                "dayOfWeek: ${aggregatedData.dayOfWeek}, value: ${aggregatedData.Co2}"
            )
        }

        return aggregatedList
    }

    fun map(): List<Map<String, Any>> {
        return co2Data.map { barData ->
            mapOf(
                "dayOfWeek" to barData.dayOfWeek, "value" to barData.Co2
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

data class ConsumptionData(val dayOfWeek: String, val Co2: Float, val weekOfYear: Int)