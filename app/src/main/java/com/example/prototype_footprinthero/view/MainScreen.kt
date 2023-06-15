package com.example.prototype_footprinthero.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.prototype_footprinthero.model.MainViewModel
import com.example.prototype_footprinthero.ui.theme.Prototype_FootPrintHeroTheme

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Footprint Hero",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(16.dp)
        )

        DropdownMenu(
            expanded = false,
            onDismissRequest = { /* Empty */ }
        ) {
            viewModel.vehicles.forEach { vehicle ->
                DropdownMenuItem(onClick = {
                    viewModel.onVehicleSelected(vehicle)
                }) {
                    Text(text = vehicle)
                }
            }
        }

        OutlinedTextField(
            value = viewModel.duration.value.toString(),
            onValueChange = { newValue ->
                viewModel.onDurationChanged(newValue.toIntOrNull() ?: 0)
            },
            label = { Text(text = "Duration") }
        )

        Button(
            onClick = { viewModel.calculateCO2() },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Calculate CO2")
            Text(text = "Calculate CO2")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "CO2: ${viewModel.co2.value} kg",
            style = MaterialTheme.typography.h6
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Prototype_FootPrintHeroTheme {
        MainScreen()
    }
}
