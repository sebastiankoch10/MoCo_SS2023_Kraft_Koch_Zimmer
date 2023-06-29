package com.example.prototype_footprinthero.view

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.prototype_footprinthero.model.ConsumptionDataList
import com.example.prototype_footprinthero.viewmodel.MainViewModel


@Composable
fun WeekdayOverview(viewModel: MainViewModel) {
    Log.i("WeekdayOverview", "WeekdayOverview start")

    Log.d("WeekdayOverview", "co2DataList Länge: ${viewModel.co2DataList.value?.co2Data?.size}")

    val aggregatedDataList: ConsumptionDataList = viewModel.co2DataList.value?.aggregateToDaysOfThisWeek(viewModel)
        ?: ConsumptionDataList(mutableListOf())


    val maxValue = aggregatedDataList.maxByOrNull { it.co2 }?.co2 ?: 0f



    Log.d("WeekdayOverview", "maxValue: $maxValue")

    Column(Modifier.padding(16.dp)) {
        Text(
            text = "Wochentagsübersicht(Tonne/Tag)",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(Modifier.fillMaxWidth()) {
            aggregatedDataList.co2Data.forEach { data ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = data.dayOfWeek)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color(0xFF467302))
                    ) {
                        val height = (maxValue - data.co2) / maxValue * 200
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height.dp)
                                .background(Color.White)
                        )
                    }
                    Text(
                        text = data.co2.toString(),
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        LaunchedEffect(viewModel.co2DataList.value) {
            // Aktionen ausführen, wenn sich der Wert von viewModel.co2DataList ändert
            Log.d(
                "WeekdayOverview",
                "viewModel.co2DataList changed: ${viewModel.co2DataList.value}"
            )
            // Hier können Sie Ihre Logik zur Aktualisierung der View einfügen
            viewModel.co2DataList.value?.let { co2DataList ->
                // Aktualisieren Sie Ihre View mit den neuen Daten
            }
        }
    }
}
