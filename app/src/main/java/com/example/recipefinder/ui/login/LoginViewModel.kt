package com.example.recipefinder.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

data class LoginUiState(
    val email: String = "",
    val password: String = ""
)

class LoginViewModel : ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set
    val accountService: AccountService = AccountServiceImpl()

    suspend fun authentication(): Boolean = suspendCoroutine { cont ->
        accountService.authenticate(uiState.value.email, uiState.value.password) { error ->
            cont.resume(error == null)
        }
    }
}