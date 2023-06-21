package com.example.prototype_footprinthero.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.prototype_footprinthero.model.ConsumptionDataList


@Composable
fun WeekdayOverview(co2DataList: ConsumptionDataList) {
    Log.i("WeekdayOverview", "WeekdayOverview start")

    Log.d("WeekdayOverview", "co2DataList länge: ${co2DataList.size()}")

    val maxValue = co2DataList.co2Data.maxByOrNull { it.value }?.value ?: 0f

    Log.d("WeekdayOverview", "maxValue: $maxValue")

    Column(Modifier.padding(16.dp)) {
        Text(
            text = "Wochentagsübersicht(Tonne/Tag)",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(Modifier.fillMaxWidth()) {
            items(co2DataList.co2Data) { data ->
                Log.d("WeekdayOverview", "dayofweek: ${data.dayOfWeek} und value: ${data.value}")
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
                        val height = (maxValue - data.value) / maxValue * 200
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height.dp)
                                .background(Color.White)
                        )
                    }
                    Text(
                        text = data.value.toString(),
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}
