package com.example.prototype_footprinthero.view

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.prototype_footprinthero.model.ConsumptionData



@Composable
fun WeekdayOverview() {
    val days = listOf("Mo", "Di", "Mi", "Do", "Fr", "Sa", "So")

    val co2Data = remember {
        mutableStateListOf(
            ConsumptionData("Mo", 5.0F),
            ConsumptionData("Di", 25.0F),
            ConsumptionData("Mi", 50.2F),
            ConsumptionData("Do", 6.5F),
            ConsumptionData("Fr", 1.1F),
            ConsumptionData("Sa", 9.0F),
            ConsumptionData("So", 69.6F)
        )
    }

    val maxValue = co2Data.maxByOrNull { it.value }?.value ?: 0f

    Column(Modifier.padding(16.dp)) {
        Text(
            text = "Wochentagsübersicht",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(Modifier.fillMaxWidth()) {
            co2Data.forEach { data ->
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
