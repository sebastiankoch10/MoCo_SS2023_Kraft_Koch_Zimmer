package com.example.prototype_footprinthero.view

import android.util.Log
import com.example.prototype_footprinthero.model.ConsumptionDataList

fun DayView(co2DataList : ConsumptionDataList) {

    val aggregatedDataList = co2DataList.aggregateByDayOfWeek()

    aggregatedDataList.forEach { data ->
        Log.d("AggregatedData", "dayOfWeek: ${data.dayOfWeek}, value: ${data.value}")
    }

}