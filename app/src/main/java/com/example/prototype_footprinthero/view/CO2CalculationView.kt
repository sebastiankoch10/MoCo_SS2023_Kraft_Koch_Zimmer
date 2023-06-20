package com.example.prototype_footprinthero.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.prototype_footprinthero.model.CO2CalculationViewModel

@Composable
fun CO2Calculation(
    viewModel: CO2CalculationViewModel
) {
    val co2 = viewModel.model.co2
    val selectedTransportation = viewModel.model.selectedTransportation
    val duration = viewModel.model.duration

    Column(Modifier.padding(16.dp)) {
        Text(text = "CO2-Berechnung", style = MaterialTheme.typography.h6)

        Row(Modifier.padding(top = 8.dp)) {
            Text(text = "Gewähltes Fortbewegungsmittel: $selectedTransportation")
        }

        Row(Modifier.padding(top = 8.dp)) {
            Text(text = "Dauer der Fortbewegung: $duration Minuten")
        }

        Button(
            onClick = { viewModel.calculateCO2() },
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
