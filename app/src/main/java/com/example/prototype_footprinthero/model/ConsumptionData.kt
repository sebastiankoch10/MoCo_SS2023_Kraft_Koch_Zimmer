package com.example.prototype_footprinthero.model


data class ConsumptionDataList(val co2Data: MutableList<Any>)


data class ConsumptionData(val dayOfWeek: String, val value: Float)