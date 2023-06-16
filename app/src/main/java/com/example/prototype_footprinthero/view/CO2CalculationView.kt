package com.example.prototype_footprinthero.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CO2Calculation(
    co2: Float,
    onCalculateCO2: () -> Unit) {
    var co2 by remember { mutableStateOf(0f) }
    val transportationCO2 = mapOf("Auto" to 0.3f, "Fahrrad" to 0.0f, "Flugzeug" to 2.0f)
    val selectedTransportation by remember { mutableStateOf("Auto") }
    val duration by remember { mutableStateOf(0) }

    Column(Modifier.padding(16.dp)) {
        Text(text = "CO2-Berechnung", style = MaterialTheme.typography.h6)

        Row(Modifier.padding(top = 8.dp)) {
            Text(text = "Gewähltes Fortbewegungsmittel: $selectedTransportation")
        }

        Row(Modifier.padding(top = 8.dp)) {
            Text(text = "Dauer der Fortbewegung: $duration Minuten")
        }

        Button(
            onClick = {
                val co2Emission = transportationCO2[selectedTransportation] ?: 0f
                val calculatedCo2 = co2Emission * duration
                if (!calculatedCo2.isNaN()) {
                    co2 = calculatedCo2
                }
            },
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp)
                .clip(MaterialTheme.shapes.medium),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF9ABF15))
        ) {
            Text(text = "Berechnen")
        }

        Row(Modifier.padding(top = 8.dp)) {
            Text(text = "CO2-Emission: ${co2.takeIf { it.isFinite() } ?: 0f} kg")
        }
    }
}