package com.example.prototype_footprinthero.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.prototype_footprinthero.model.ConsumptionDataList
import com.example.prototype_footprinthero.viewmodel.MainViewModel


@Composable
fun DayView(viewModel: MainViewModel) {
    Log.i("DayView", "DayView called")

    val aggregatedDataList = rememberUpdatedState(viewModel.co2DataList.value?.aggregateToDaysOfThisWeek(viewModel)
        ?: ConsumptionDataList(mutableListOf())).value

    val weekdayInGerman = viewModel.dayInGerman
    val thData = aggregatedDataList.find { it.dayOfWeek == weekdayInGerman }
    Log.d("DayView", "thData: $thData")
    val fillAmount: Float

    if (thData != null) {
        if (thData.co2 > 6f) {
            fillAmount = 6f
            Log.d("DayView", "TagesData: $thData")
        } else {
            fillAmount = thData.co2
            Log.d("DayView", "TagesData: $thData")
        }
        DrawHorizontalProgressBar(fillAmount)
    }
    LaunchedEffect(viewModel.co2DataList.value) {
        // Aktionen ausführen, wenn sich der Wert von viewModel.co2DataList ändert
        Log.d("DayView", "viewModel.co2DataList changed: ${viewModel.co2DataList.value}")
        // Hier können Sie Ihre Logik zur Aktualisierung der View einfügen
    }
}


@Composable
fun DrawHorizontalProgressBar(fillAmount: Float) {
    Log.d("DayView", "DrawHorizontalProgressBar called with fillAmount: $fillAmount")
    val cappedFillAmount = fillAmount.coerceIn(0f, 6f)

    Column {
        Text(
            text = "Tagesanzeige", modifier = Modifier.padding(8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(16.dp)
                .background(color = Color.LightGray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(color = if (cappedFillAmount >= 6f) Color.Red else Color.Green)
                    .fillMaxWidth((cappedFillAmount / 6f).coerceIn(0f, 1f))
            )
        }
    }
}


