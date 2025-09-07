package com.adamnoorapps.recipefinder.ui.login

interface AccountService {
    fun createAnonymousAccount(onResult: (Throwable?) -> Unit)
    fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
    fun signOut()
    fun signUp(email: String, password: String, confirmPassword: String, onResult: (Throwable?) -> Unit)
    fun editDisplayName(displayName: String, onResult: (Throwable?) -> Unit)
}