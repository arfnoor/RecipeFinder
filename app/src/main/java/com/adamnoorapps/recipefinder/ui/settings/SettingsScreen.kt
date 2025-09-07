package com.adamnoorapps.recipefinder.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.adamnoorapps.recipefinder.ui.login.AccountServiceImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SettingsScreen(
    signOut : () -> Unit,
)
{
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Display Name: ${Firebase.auth.currentUser?.displayName ?: "Not set"}")
            Button(
                onClick = { AccountServiceImpl().editDisplayName("Hey", {}) },
            ) {
                Text("Edit")
            }
        }
        Button(onClick = { signOut() }) {
            Text("Sign Out")
        }
    }

}