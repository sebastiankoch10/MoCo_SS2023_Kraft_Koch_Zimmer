import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prototype_footprinthero.ui.theme.Prototype_FootPrintHeroTheme
import com.example.prototype_footprinthero.view.DayView
import com.example.prototype_footprinthero.view.MonthlyOverview
import com.example.prototype_footprinthero.view.TransportationButtonsView
import com.example.prototype_footprinthero.view.TransportationDurationView
import com.example.prototype_footprinthero.view.WeekdayOverview
import com.example.prototype_footprinthero.view.WeeklyOverview
import com.example.prototype_footprinthero.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Footprint Hero",
                        style = MaterialTheme.typography.h5,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                backgroundColor = Color(0xFF214001),
                elevation = 0.dp
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Berechne deinen CO2-Fußabdruck",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp)
            )

            TransportationButtonsView(
                vehicles = viewModel.vehicles,
                selectedVehicle = viewModel.selectedVehicle.value,
                onVehicleSelected = viewModel::onVehicleSelected
            )

            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                TransportationDurationView(
                    durationInMinutes = viewModel.duration,
                    onDurationChanged = { duration ->
                        viewModel.onDurationChanged(duration)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { viewModel.calculateCO2() },
                    modifier = Modifier
                        .background(Color(0xFF00FF00))
                ) {
                    Text(text = "Berechnen", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Überblick",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(16.dp)
            )

            DayView(viewModel)
            WeekdayOverview(viewModel)
            WeeklyOverview(viewModel)
            MonthlyOverview(viewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    val viewModel = MainViewModel()

    Prototype_FootPrintHeroTheme {
        MainScreen(viewModel = viewModel)
    }
}