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
    vehicles: List<String>, selectedVehicle: String, onVehicleSelected: (String) -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Text(text = "Fortbewegungsmittel", style = MaterialTheme.typography.h6)

        // Buttons in einer Rasteranordnung
        val columnCount = 3 // Anzahl der Spalten
        val rowCount = (vehicles.size + columnCount - 1) / columnCount

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            for (rowIndex in 0 until rowCount) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val startIndex = rowIndex * columnCount
                    val endIndex = minOf(startIndex + columnCount, vehicles.size)
                    val rowVehicles = vehicles.subList(startIndex, endIndex)

                    rowVehicles.forEach { vehicle ->
                        Button(
                            onClick = { onVehicleSelected(vehicle) },
                            modifier = Modifier.padding(horizontal = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = if (selectedVehicle == vehicle) Color.Blue else MaterialTheme.colors.primary,
                                contentColor = Color.White
                            )
                        ) {
                            Text(text = vehicle)
                        }
                    }
                }
            }
        }
    }
}



















