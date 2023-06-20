package com.example.prototype_footprinthero.model


data class ConsumptionDataList(val co2Data: MutableList<ConsumptionData>) {
    fun addConsumption(consumptionData: ConsumptionData) {
        co2Data.add(consumptionData)
    }

    fun map(any: Any): Any {

    }
}


data class ConsumptionData(val dayOfWeek: String, val value: Float)