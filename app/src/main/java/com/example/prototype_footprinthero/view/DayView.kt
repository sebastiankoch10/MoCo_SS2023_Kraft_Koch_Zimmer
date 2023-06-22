package com.example.prototype_footprinthero.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.prototype_footprinthero.model.ConsumptionDataList

@Composable
fun DayView(co2DataList: ConsumptionDataList) {
    Log.i("DayView", "DayView called")

    val aggregatedDataList: ConsumptionDataList = co2DataList.aggregateByDayOfWeek()
    val thData = aggregatedDataList.find { it.dayOfWeek == "TH" }
    Log.d("DayView", "TagesData: $thData")

    if (thData != null) {
        val fillAmount = thData.value / 6f // Annahme: Wertebereich ist 0-6
        DrawHorizontalProgressBar(fillAmount)
    }
}

@Composable
fun DrawHorizontalProgressBar(fillAmount: Float) {
    Log.d("DayView", "DrawHorizontalProgressBar called with fillAmount: $fillAmount")
    Column {
        Text(
            text = "Tagesanzeige",
            modifier = Modifier.padding(8.dp)
        )
        Box(
            modifier = Modifier.fillMaxWidth().height(16.dp).background(color = Color.LightGray)
        ) {
            Box(
                modifier = Modifier
                    .width((fillAmount / 6f).coerceIn(0f, 1f).dp)
                    .fillMaxHeight()
                    .background(color = if (fillAmount >= 6f) Color.Red else Color.Green)
            )
        }
    }
}
