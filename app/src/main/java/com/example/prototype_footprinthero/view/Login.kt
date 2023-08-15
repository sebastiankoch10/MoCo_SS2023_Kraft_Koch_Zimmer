package com.example.prototype_footprinthero.view

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.prototype_footprinthero.ui.theme.Prototype_FootPrintHeroTheme

data class User(val username: String, val password: String)

val userList = listOf(
    User("123", "123"),
    User("benutzer2", "passwort2"),
    // Weitere Benutzer hinzufügen...
)

@Composable
fun Login(onLoginSuccess: () -> Unit) {
    val errorMessage = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val isLoggedInState = remember { mutableStateOf(false) } // Hinzugefügt

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text(text = "Benutzername") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text(text = "Passwort") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { performLogin(username.value, password.value, errorMessage, isLoggedInState, onLoginSuccess) }, // Aktualisiert
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Anmelden")
        }

        Text(
            text = errorMessage.value,
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

private fun performLogin(
    username: String,
    password: String,
    errorMessage: MutableState<String>,
    isLoggedInState: MutableState<Boolean>,
    onLoginSuccess: () -> Unit
) {
    for (user in userList) {
        if (user.username == username && user.password == password) {
            isLoggedInState.value = true
            onLoginSuccess()
            return
        }
    }
    errorMessage.value = "Ungültige Anmeldeinformationen"
}








@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    Prototype_FootPrintHeroTheme {
        Login(onLoginSuccess = {})
    }
}