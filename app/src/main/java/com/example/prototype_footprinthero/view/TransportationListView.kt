package com.example.prototype_footprinthero.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


@Composable
fun TransportationListView(
    vehicles: List<String>,
    selectedVehicle: String,
    onVehicleSelected: (String) -> Unit
) {
    Column(Modifier.padding(16.dp)) {
        Text(text = "Fortbewegungsmittel", style = MaterialTheme.typography.h6)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF9ABF15))
        ) {
            var expanded by remember { mutableStateOf(false) }
            val density = LocalDensity.current
            val dropDownMenuWidth = with(density) { 200.dp.toPx() }

            Text(
                text = selectedVehicle,
                style = MaterialTheme.typography.body1.merge(),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .clickable { expanded = true }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { dropDownMenuWidth.toDp() })
                    .align(Alignment.CenterEnd)
            ) {
                vehicles.forEach { vehicle ->
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onVehicleSelected(vehicle)
                        },
                        modifier = Modifier
                            .clickable { onVehicleSelected(vehicle) }
                    ) {
                        Text(text = vehicle)
                    }
                }
            }
        }
    }
}


