package com.example.recipefinder.ui.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun LoginModal(refresh : () -> Unit,viewModel: LoginViewModel = viewModel<LoginViewModel>()) {
    val uiState = remember{viewModel.uiState}
    val coroutineScope = rememberCoroutineScope()
    val signUp = remember{mutableStateOf(false)}
    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }
    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }
    fun onConfirmPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(confirmPassword = newValue)
    }
    if(!signUp.value) {
        Column() {
            EmailField(uiState.value.email, { onEmailChange(it) }, Modifier.fillMaxWidth())
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
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    signUp.value = true
                }
            ) {
                Text("Sign Up")
            }
        }
    }
    else{
        Column() {
            EmailField(uiState.value.email, { onEmailChange(it) }, Modifier.fillMaxWidth())
            PasswordField(uiState.value.password, { onPasswordChange(it) }, Modifier.fillMaxWidth())
            ConfirmPasswordField(uiState.value.confirmPassword, { onConfirmPasswordChange(it) }, Modifier.fillMaxWidth())
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    coroutineScope.launch {
                        val good = viewModel.signUp()
                        if (good) {
                            val good2 = viewModel.authentication()
                            if (good2) {
                                refresh()
                            }
                        }
                    }
                }
            ) {
                Text("Sign Up")
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    signUp.value = false
                }
            ) {
                Text("Cancel")
            }
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

@Composable
fun ConfirmPasswordField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        singleLine = true,
        modifier = modifier,
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text("Confirm Password") },
        visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
    )
}