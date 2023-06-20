package com.example.prototype_footprinthero.model


data class ConsumptionDataList(val co2Data: MutableList<ConsumptionData>) {
    fun addConsumption(consumptionData: ConsumptionData) {
        co2Data.add(consumptionData)
    }

    fun map(): List<Map<String, Any>> {
        return co2Data.map { barData ->
            mapOf(
                "dayOfWeek" to barData.dayOfWeek,
                "value" to barData.value
            )
        }
    }
}

data class ConsumptionData(val dayOfWeek: String, val value: Float)