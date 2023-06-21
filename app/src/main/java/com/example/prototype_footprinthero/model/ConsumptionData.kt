package com.example.prototype_footprinthero.model

import com.example.prototype_footprinthero.observers.DataObserver


data class ConsumptionDataList(val co2Data: MutableList<ConsumptionData>) {

    private val observers: MutableList<DataObserver> = mutableListOf()

    fun registerObserver(observer: DataObserver) {
        observers.add(observer)
    }

    fun unregisterObserver(observer: DataObserver) {
        observers.remove(observer)
    }
    private fun notifyObservers() {
        observers.forEach { observer ->
            observer.onDataChangedFromObserver()
        }
    }

    fun size(): Int {
        return co2Data.size
    }

    fun addConsumption(consumptionData: ConsumptionData) {
        co2Data.add(consumptionData)
        notifyObservers()
    }

    fun map(): List<Map<String, Any>> {
        return co2Data.map { barData ->
            mapOf(
                "dayOfWeek" to barData.dayOfWeek,
                "value" to barData.value
            )
        }
    }

    fun maxByOrNull(selector: (ConsumptionData) -> Float): ConsumptionData? {
        return co2Data.maxByOrNull(selector)
    }


    fun forEach(action: (ConsumptionData) -> Unit) {
        co2Data.forEach(action)
    }
}

data class ConsumptionData(val dayOfWeek: String, val value: Float)