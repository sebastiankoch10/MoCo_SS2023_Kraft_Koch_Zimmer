package com.example.prototype_footprinthero.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TransportationDurationView(
    duration: Int,
    onDurationChanged: (Int) -> Unit
) {
    var currentDuration by remember { mutableStateOf(duration.toInt()) }

    Column(Modifier.padding(16.dp)) {
        Text(text = "Fortbewegungsdauer (in Minuten)", style = MaterialTheme.typography.h6)

        Box(Modifier.padding(top = 8.dp)) {
            Slider(
                value = currentDuration.toFloat(),
                onValueChange = { newValue ->
                    currentDuration = newValue.toInt()
                    onDurationChanged(currentDuration)
                },
                valueRange = 0f..120f,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = currentDuration.toString(),
                style = MaterialTheme.typography.body1.merge(),
                modifier = Modifier.align(Alignment.BottomEnd).padding(end = 8.dp)
            )
        }
    }
}








