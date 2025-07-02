package com.example.recipefinder.ui.theme

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Primary = Color(0xFF70CF97) // Green middle
val Secondary = Color(0xFF3F7355) // Green dark
val Tertiary = Color(0xFFB2E4C7) // Green light

@Composable
fun dialogFieldColors() = OutlinedTextFieldDefaults.colors(
    unfocusedContainerColor = Primary,
    unfocusedTextColor = Color.White,
    unfocusedBorderColor = Color.Transparent,
    unfocusedLabelColor = Color.White,
    focusedContainerColor = Primary,
    focusedLabelColor = Color.White,
    focusedBorderColor = Color.White,
    focusedTextColor = Color.White
)

@Composable
fun inputFieldColors() = OutlinedTextFieldDefaults.colors(
    unfocusedContainerColor = Color.White,
    unfocusedTextColor = Color.Black,
    unfocusedBorderColor = Color.Transparent,
    unfocusedLabelColor = Color.Transparent,
    focusedContainerColor = Color.White,
    focusedLabelColor = Color.Transparent,
    focusedBorderColor = Secondary,
    focusedTextColor = Color.Black
)