package com.example.prototype_footprinthero.view

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.prototype_footprinthero.viewmodel.MainViewModel

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
                        Text(
                            text = "Footprint Hero",
                            style = MaterialTheme.typography.h5
                        )
                    }
                },
                backgroundColor = Color(0xFF214001),
                elevation = 0.dp
            )
        },
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

            Row(
                Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    TransportationDurationView(
                        durationInMinutes = viewModel.duration,
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

            DayView(viewModel)
            WeekdayOverview(viewModel)

            //TODO richtig?
            LaunchedEffect(viewModel.co2DataList.value) {
                // Aktionen ausführen, wenn sich der Wert von viewModel.co2DataList ändert
                Log.d("MainScreen", "viewModel.co2DataList changed: ${viewModel.co2DataList.value}")
                // Hier können Sie Ihre Logik zur Aktualisierung der View einfügen
                viewModel.co2DataList.value?.let { co2DataList ->
                    // Aktualisieren Sie Ihre View mit den neuen Daten
                }
            }
        }
    }
}

