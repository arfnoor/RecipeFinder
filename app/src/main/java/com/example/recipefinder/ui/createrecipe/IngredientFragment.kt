package com.example.recipefinder.ui.createrecipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.data.Ingredient
import com.example.recipefinder.ui.theme.Primary
import com.example.recipefinder.ui.theme.Secondary
import com.example.recipefinder.ui.theme.dialogFieldColors

@Composable
fun IngredientInputDialog(
    onConfirm: (Ingredient) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    androidx.compose.material3.AlertDialog(
        containerColor = Secondary,
        onDismissRequest = onDismiss,
        title = { Text(color = Color.White, text = "Add Ingredient") },
        text = {
            Column {
                OutlinedTextField(
                    colors = dialogFieldColors(),
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    OutlinedTextField(
                        colors = dialogFieldColors(),
                        value = quantity,
                        onValueChange = { quantity = it },
                        label = { Text("Quantity") },
                        modifier = Modifier.fillMaxWidth(.4f)
                    )
                    var expanded by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, top = 8.dp)
                            .height(56.dp)
                            .background(Primary, shape = RoundedCornerShape(2.dp))
                            .clickable { expanded = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (unit.isNotBlank()) unit else "Select unit \u25BC",
                            color = Color.White,
                            fontSize = 16.sp,
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .fillMaxWidth(.3f)
                        ) {
                            com.example.recipefinder.data.Unit.entries.forEach { enumUnit ->
                                DropdownMenuItem(
                                    text = { Text(enumUnit.name.lowercase().replaceFirstChar(Char::titlecase), fontSize = 16.sp) },
                                    onClick = {
                                        unit = enumUnit.name
                                        expanded = false
                                    }
                                )
                            }

                        }
                    }
                }
                OutlinedTextField(
                    colors = dialogFieldColors(),
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Note") }
                )
            }
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = Color.White
                ),
                onClick = {
                    if (name.isNotBlank() && quantity.isNotBlank() && unit.isNotBlank()) {
                        onConfirm(Ingredient(0, name.lowercase().replaceFirstChar(Char::titlecase), quantity.toInt(), com.example.recipefinder.data.Unit.valueOf(unit.uppercase()), note))
                    }
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Primary
                ),
            ) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun EditIngredientInputDialog(
    ingredient: Ingredient,
    onConfirm: (Ingredient) -> Unit,
    onDismiss: () -> Unit,
    onRemove: () -> Unit
) {
    val newName = remember { mutableStateOf(ingredient.name) }
    val newQuantity = remember { mutableIntStateOf(ingredient.quantity) }
    val newUnit = remember { mutableStateOf(ingredient.unit.name) }
    val newNote = remember { mutableStateOf(ingredient.note) }

    androidx.compose.material3.AlertDialog(
        containerColor = Secondary,
        onDismissRequest = onDismiss,
        title = { Text("Edit Ingredient", color = Color.White) },
        text = {
            Column {
                OutlinedTextField(
                    colors = dialogFieldColors(),
                    value = newName.value,
                    onValueChange = { newName.value = it },
                    label = { Text("Name") },
                    placeholder = { Text(newName.value) },
                )
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        colors = dialogFieldColors(),
                        value = newQuantity.intValue.toString(),
                        onValueChange = { value ->
                            value.toIntOrNull()?.let { newQuantity.intValue = it }
                        },
                        label = { Text("Quantity") },
                        placeholder = { Text(newQuantity.intValue.toString()) },
                        modifier = Modifier.fillMaxWidth(.4f),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                    var expanded by remember { mutableStateOf(false) }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, top = 8.dp)
                            .height(56.dp)
                            .background(Primary, shape = RoundedCornerShape(2.dp))
                            .clickable { expanded = true },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (newUnit.value.isNotBlank()) newUnit.value else "Select unit \u25BC",
                            color = Color.White,
                            fontSize = 16.sp,
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        com.example.recipefinder.data.Unit.entries.forEach { enumUnit ->
                            DropdownMenuItem(
                                text = { Text(enumUnit.name.lowercase().replaceFirstChar(Char::titlecase)) },
                                onClick = {
                                    newUnit.value = enumUnit.name
                                    expanded = false
                                }
                            )
                        }

                    }
                }
                OutlinedTextField(
                    colors = dialogFieldColors(),
                    value = newNote.value,
                    onValueChange = { newNote.value = it },
                    label = { Text("Note") },
                    placeholder = { Text(newNote.value) },
                )
                Button(
                    onClick = onRemove,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ),
                ) {
                    Text(
                        text = "Remove Ingredient",
                        color = Color.White,
                        modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 8.dp)
                    )
                }
            }

        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = Color.White
                ),
                onClick = {onConfirm(Ingredient(ingredient.id, newName.value.lowercase().replaceFirstChar(Char::titlecase), newQuantity.intValue, com.example.recipefinder.data.Unit.valueOf(newUnit.value.uppercase())))}
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Primary
                ),
            ) {
                Text("Cancel")
            }
        }
    )
}