package com.example.prototype_footprinthero.view

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.prototype_footprinthero.model.ConsumptionDataList
import com.example.prototype_footprinthero.viewmodel.MainViewModel
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState



@Composable
fun WeekdayOverview(viewModel: MainViewModel) {
    Log.i("WeekdayOverview", "WeekdayOverview start")

    val co2DataList by viewModel.co2DataList.observeAsState(ConsumptionDataList(mutableListOf()))
    val aggregatedDataList = co2DataList.aggregateToDaysOfThisWeek(viewModel) //TODO an viemodel hängen
    val maxValue = aggregatedDataList.maxByOrNull { it.co2 }?.co2 ?: 0f

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
    }
}



