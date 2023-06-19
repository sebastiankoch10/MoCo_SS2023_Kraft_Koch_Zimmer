package com.example.prototype_footprinthero.view

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.prototype_footprinthero.model.BarData
import com.example.prototype_footprinthero.model.MainViewModel
import com.google.firebase.firestore.FirebaseFirestore

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
