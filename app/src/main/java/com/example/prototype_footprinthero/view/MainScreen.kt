package com.example.prototype_footprinthero.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.prototype_footprinthero.model.BarData
import com.example.prototype_footprinthero.model.MainViewModel
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun MainScreen(viewModel: MainViewModel, firestoreDatabase: FirebaseFirestore) {
    val co2DataInput = remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "CO2 Calculator",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.primary
        )

        TextField(
            value = co2DataInput.value,
            onValueChange = { co2DataInput.value = it },
            label = { Text("CO2-Daten") },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Button(
            onClick = {
                val co2Data = co2DataInput.value.text
                if (co2Data.isNotEmpty()) {
                    val barData = BarData("Monday", co2Data.toFloat())
                    viewModel.writeCO2Data(barData)
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Berechnen")
        }
    }
}