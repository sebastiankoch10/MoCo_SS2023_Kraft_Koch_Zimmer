package com.example.prototype_footprinthero.model


data class ConsumptionDataList(val co2Data: MutableList<ConsumptionData>) {
    fun size(): Int {
        return co2Data.size
    }

    fun addConsumption(consumptionData: ConsumptionData) {
        co2Data.add(consumptionData)
    }

    fun aggregateByDayOfWeek(): List<ConsumptionData> {
        val aggregatedList = mutableListOf<ConsumptionData>()

        co2Data.groupBy { it.dayOfWeek }.forEach { (_, dataList) ->
            val totalValue = dataList.sumOf { it.value.toDouble() }.toFloat()
            val firstData = dataList.first()
            val aggregatedData = ConsumptionData(firstData.dayOfWeek, totalValue)
            aggregatedList.add(aggregatedData)
        }

        return aggregatedList
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