package com.example.prototype_footprinthero.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.prototype_footprinthero.viewmodel.MainViewModel
import com.example.prototype_footprinthero.model.ConsumptionDataList

@Composable
fun MonthlyOverview(viewModel: MainViewModel) {
    val co2DataList by viewModel.co2DataList.collectAsState()
    val aggregatedDataList = co2DataList.aggregateToDaysOfThisWeek(viewModel)
    val maxValue = aggregatedDataList.maxByOrNull { it.co2 }?.co2 ?: 0f

    val months = listOf(
        "Januar", "Februar", "März", "April",
        "Mai", "Juni", "Juli", "August",
        "September", "Oktober", "November", "Dezember"
    )
    var selectedMonth by remember { mutableStateOf(0) }

    Column(Modifier.padding(16.dp)) {
        Text(text = "Monatsübersicht", style = MaterialTheme.typography.h6, color = Color.White)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD4D93D))
        ) {
            DropdownMenu(
                expanded = false,
                onDismissRequest = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                months.forEachIndexed { index, month ->
                    DropdownMenuItem(onClick = { selectedMonth = index }) {
                        Text(text = month, color = Color.Black)
                    }
                }
            }
            if (selectedMonth != 0) {
                Text(
                    text = months[selectedMonth],
                    style = MaterialTheme.typography.body1.merge(),
                    color = Color.Black,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterStart)
                )
            }
        }
    }
}