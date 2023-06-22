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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WeeklyOverview() {
    val weeks = (1..52).toList()
    var selectedWeek by remember { mutableStateOf(0) }

    Column(Modifier.padding(16.dp)) {
        Text(text = "Wochenübersicht", style = MaterialTheme.typography.h6)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFD4D93D))
        ) {
            DropdownMenu(
                expanded = false, onDismissRequest = {}, modifier = Modifier.fillMaxWidth()
            ) {
                weeks.forEach { week ->
                    DropdownMenuItem(onClick = {
                        selectedWeek = week
                    }) {
                        Text(text = "KW $week")
                    }
                }
            }
            if (selectedWeek != 0) {
                Text(
                    text = "KW $selectedWeek",
                    style = MaterialTheme.typography.body1.merge(),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterStart)
                )
            }
        }
    }
}