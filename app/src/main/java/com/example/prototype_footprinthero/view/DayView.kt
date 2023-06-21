package com.example.prototype_footprinthero.view

import android.util.Log
import androidx.compose.runtime.Composable
import com.example.prototype_footprinthero.model.ConsumptionDataList

@Composable
fun DayView(co2DataList : ConsumptionDataList) {

    val aggregatedDataList = co2DataList.aggregateByDayOfWeek()

    aggregatedDataList.forEach { data ->
        Log.d("AggregatedData", "dayOfWeek: ${data.dayOfWeek}, value: ${data.value}")
    }

}