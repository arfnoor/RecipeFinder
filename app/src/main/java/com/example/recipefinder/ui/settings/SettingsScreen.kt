package com.example.recipefinder.ui.settings

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SettingsScreen(
    signOut : () -> Unit,
)
{
    Button(onClick = { signOut() }) {
        Text("Sign Out")
    }
}