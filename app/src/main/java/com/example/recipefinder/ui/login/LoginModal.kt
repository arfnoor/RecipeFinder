package com.example.recipefinder.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun LoginModal(refresh : () -> Unit,viewModel: LoginViewModel = viewModel<LoginViewModel>()) {
    val uiState = viewModel.uiState
    val coroutineScope = rememberCoroutineScope()
    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }
    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }
    Column() {
        EmailField(uiState.value.email, {onEmailChange(it)}, Modifier.fillMaxWidth())
        PasswordField(uiState.value.password, { onPasswordChange(it) }, Modifier.fillMaxWidth())
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                coroutineScope.launch {
                    val good = viewModel.authentication()
                    if (good) {
                        refresh()
                    }
                }
            }
        ) {
            Text("Login")
        }
    }
}



@Composable
fun EmailField(value: String,  onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text("Email") },
//        leadingIcon = { [...] }
    )
}

@Composable
fun PasswordField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text("Password") },
        visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
    )
}