package com.example.prototype_footprinthero.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.prototype_footprinthero.model.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Footprint Hero", style = MaterialTheme.typography.h5)
                    }
                },
                backgroundColor = Color(0xFF214001),
                elevation = 0.dp
            )
        },
        /* TODO was soll der machen?
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                backgroundColor = Color(0xFF214001),
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
         */
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            TransportationButtonsView(
                vehicles = viewModel.vehicles,
                selectedVehicle = viewModel.selectedVehicle.value,
                onVehicleSelected = viewModel::onVehicleSelected
            )

            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f)) {
                    TransportationDurationView(
                        duration = viewModel.duration,
                        onDurationChanged = { duration ->
                            viewModel.onDurationChanged(duration)
                        }
                    )

                }
                Button(
                    onClick = { viewModel.calculateCO2() },
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Berechnen")
                }
            }

            WeekdayOverview()
            WeeklyOverview()
        }
    }
}









