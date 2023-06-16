package com.example.prototype_footprinthero.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun TransportationButtonsView(
    vehicles: List<String>,
    selectedVehicle: String,
    onVehicleSelected: (String) -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Text(text = "Fortbewegungsmittel", style = MaterialTheme.typography.h6)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            vehicles.forEach { vehicle ->
                Button(
                    onClick = { onVehicleSelected(vehicle) },
                    modifier = Modifier.padding(horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (selectedVehicle == vehicle) Color.Blue else MaterialTheme.colors.primary,
                        contentColor = Color.White // Specify the text color of the button
                    )
                ) {
                    Text(text = vehicle)
                }
            }
        }
    }
}











