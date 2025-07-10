package com.example.recipefinder.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.recipefinder.ui.theme.Primary
import com.example.recipefinder.ui.theme.Secondary
import kotlinx.coroutines.launch

@Composable
fun LoginModal(refresh : () -> Unit,viewModel: LoginViewModel = viewModel<LoginViewModel>()) {
    val uiState = remember{viewModel.uiState}
    val isLoading = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val signUp = remember{mutableStateOf(false)}
    val badSignIn = remember { mutableStateOf(false) }
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black.copy(alpha = 0.6f))
        )
        {
            Column(
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    .align(alignment = Alignment.Center)
                    .fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Login to Recipedia",
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = Secondary,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontSize = 36.sp, shadow = Shadow(color = Color.Gray, offset = Offset(1f, 1f), blurRadius = 2f))
                )
                EmailField(uiState.value.email, { onEmailChange(it) }, Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                PasswordField(uiState.value.password, { onPasswordChange(it) }, Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                Button(
                    modifier = Modifier,
                    colors = ButtonColors(containerColor = Primary, contentColor = Color.White, disabledContentColor = Color.White, disabledContainerColor = Color.White),
                    onClick = {
                        coroutineScope.launch {
                            isLoading.value = true
                            val goodSignIn = viewModel.authentication()
                            isLoading.value = false
                            if (goodSignIn) {
                                refresh()
                            } else {
                                badSignIn.value = true
                            }
                        }
                    }
                ) {
                    if (isLoading.value) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.CenterVertically)
                                .size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Login")
                    }
                }

                if (badSignIn.value) {
                    Text("Login failed, please verify email and password.", color = Color.Red)
                }
                Button(
                    modifier = Modifier.padding(bottom = 16.dp),
                    colors = ButtonColors(containerColor = Secondary, contentColor = Color.White, disabledContentColor = Color.White, disabledContainerColor = Color.White),
                    onClick = {
                        badSignIn.value = false
                        signUp.value = true
                    }
                ) {
                    Text("Sign Up")
                }
            }
        }
    }
    else {
        val badSignUp = remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.6f))
        ){
            Column(
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))
                    .align(alignment = Alignment.Center)
                    .fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Sign Up for Recipedia",
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    textAlign = TextAlign.Center,
                    color = Secondary,
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontSize = 36.sp, shadow = Shadow(color = Color.Gray, offset = Offset(1f, 1f), blurRadius = 2f))
                )
                EmailField(uiState.value.email, { onEmailChange(it) }, Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                PasswordField(
                    uiState.value.password,
                    { onPasswordChange(it) },
                    Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                ConfirmPasswordField(
                    uiState.value.confirmPassword,
                    { onConfirmPasswordChange(it) },
                    Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
                Button(
                    modifier = Modifier,
                    colors = ButtonColors(containerColor = Primary, contentColor = Color.White, disabledContentColor = Color.White, disabledContainerColor = Color.White),
                    onClick = {
                        coroutineScope.launch {
                            isLoading.value = true
                            val goodSignUp = viewModel.signUp()
                            if (goodSignUp) {
                                val goodSignIn = viewModel.authentication()
                                if (goodSignIn) {
                                    refresh()
                                }
                            } else {
                                isLoading.value = false
                                badSignUp.value = true
                            }
                        }
                    }
                ) {
                    if (isLoading.value) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.CenterVertically)
                                .size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Sign Up")
                    }
                }
                if (badSignUp.value) {
                    Text("Sign Up failed, please verify that passwords match.", color = Color.Red)
                }
                if (badSignIn.value) {
                    Text("Login failed, please try again.", color = Color.Red)
                }
                Button(
                    modifier = Modifier.padding(bottom = 16.dp),
                    colors = ButtonColors(containerColor = Secondary, contentColor = Color.White, disabledContentColor = Color.White, disabledContainerColor = Color.White),
                    onClick = {
                        badSignIn.value = false
                        badSignUp.value = false
                        signUp.value = false
                    }
                ) {
                    Text("Cancel")
                }
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