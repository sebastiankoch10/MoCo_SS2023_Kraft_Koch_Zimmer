package com.example.prototype_footprinthero.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prototype_footprinthero.ui.theme.Prototype_FootPrintHeroTheme
import com.example.prototype_footprinthero.viewmodel.MainViewModel

@Composable
fun LoginScreen(viewModel: MainViewModel, onLoginClicked: (String, String) -> Unit) {
    val usernameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            TextField(
                value = usernameState.value,
                onValueChange = { usernameState.value = it },
                label = { Text(text = "Username") },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            TextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation(),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = {
                val username = usernameState.value
                val password = passwordState.value
                val isValid = viewModel.performLogin(username, password)
                if (isValid) {
                    onLoginClicked(username, password)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    Prototype_FootPrintHeroTheme {
        val viewModel = MainViewModel()

        LoginScreen(viewModel = viewModel, onLoginClicked = { _, _ -> })
    }
}
