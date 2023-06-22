package com.example.prototype_footprinthero.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TransportationDurationView(
    durationInMinutes: Int,
    onDurationChanged: (Int) -> Unit
) {
    var hours by remember { mutableStateOf(durationInMinutes / 60) }
    var minutes by remember { mutableStateOf(durationInMinutes % 60) }

    Column(Modifier.padding(16.dp)) {
        Text(text = "Fortbewegungsdauer", style = MaterialTheme.typography.h6)

        Row(Modifier.padding(top = 8.dp)) {
            TextField(
                value = hours.toString(),
                onValueChange = { value ->
                    hours = value.toIntOrNull() ?: 0
                    updateDuration(hours, minutes, onDurationChanged)
                },
                label = { Text("Stunden") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )

            Spacer(Modifier.width(8.dp))

            TextField(
                value = minutes.toString(),
                onValueChange = { value ->
                    minutes = value.toIntOrNull() ?: 0
                    updateDuration(hours, minutes, onDurationChanged)
                },
                label = { Text("Minuten") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private fun updateDuration(hours: Int, minutes: Int, onDurationChanged: (Int) -> Unit) {
    val newDuration = hours * 60 + minutes
    onDurationChanged(newDuration)
}











