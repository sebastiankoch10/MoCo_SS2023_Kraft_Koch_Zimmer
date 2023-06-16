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
import com.example.prototype_footprinthero.model.BarData

@Composable
fun WeekdayOverview() {
    val days = listOf("Mo", "Di", "Mi", "Do", "Fr", "Sa", "So")

    val co2Data = remember {
        mutableStateListOf(
            BarData("Mo", 0f),
            BarData("Di", 0f),
            BarData("Mi", 0f),
            BarData("Do", 0f),
            BarData("Fr", 0f),
            BarData("Sa", 0f),
            BarData("So", 0f)
        )
    }

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
                    Text(text = data.label)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color(0xFF467302))
                    ) {
                        val height = data.value / 10f
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