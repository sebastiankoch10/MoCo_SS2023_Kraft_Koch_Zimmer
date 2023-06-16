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
fun TransportationList(
    vehicles: List<String>,
    selectedVehicle: String,
    onVehicleSelected: (String) -> Unit) {
    val vehicles = listOf("Auto", "Fahrrad", "Flugzeug")
    var selectedVehicle by remember { mutableStateOf("Auto") }

    Column(Modifier.padding(16.dp)) {
        Text(text = "Fortbewegungsmittel", style = MaterialTheme.typography.h6)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF9ABF15))
        ) {
            DropdownMenu(
                expanded = false,
                onDismissRequest = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                vehicles.forEach { vehicle ->
                    DropdownMenuItem(
                        onClick = {
                            selectedVehicle = vehicle
                            onVehicleSelected(vehicle)
                        }
                    ) {
                        Text(text = vehicle)
                    }
                }
            }
            Text(
                text = selectedVehicle,
                style = MaterialTheme.typography.body1.merge(),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp)
            )
        }
    }
}