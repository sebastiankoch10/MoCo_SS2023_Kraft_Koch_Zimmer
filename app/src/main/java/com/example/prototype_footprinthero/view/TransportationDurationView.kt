package com.example.prototype_footprinthero.view

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun TransportationDurationView(
    durationInMinutes: Int,
    onDurationChanged: (Int) -> Unit
) {
    val context = LocalContext.current
    val selectedTime = remember { mutableStateOf(Pair(0, 0)) }

    Column(Modifier.padding(16.dp)) {
        Text(text = "Fortbewegungsdauer", style = MaterialTheme.typography.h6)

        Row(Modifier.padding(top = 8.dp)) {
            Button(
                onClick = { openTimePickerDialog(context, selectedTime) },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "${selectedTime.value.first} Stunden ${selectedTime.value.second} Minuten")
            }
        }
    }

    LaunchedEffect(selectedTime.value) {
        val newDuration = selectedTime.value.first * 60 + selectedTime.value.second
        onDurationChanged(newDuration)
    }
}

private fun openTimePickerDialog(context: Context, selectedTime: MutableState<Pair<Int, Int>>) {
    val currentTime = selectedTime.value.first to selectedTime.value.second

    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, hourOfDay: Int, minute: Int ->
            // Aktualisiere die ausgewählte Zeit
            selectedTime.value = hourOfDay to minute
        },
        currentTime.first,
        currentTime.second,
        true
    )

    timePickerDialog.show()
}

