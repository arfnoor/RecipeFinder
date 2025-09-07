package com.adamnoorapps.recipefinder.ui.createrecipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.adamnoorapps.recipefinder.data.Step
import com.adamnoorapps.recipefinder.ui.theme.Primary
import com.adamnoorapps.recipefinder.ui.theme.Secondary
import com.adamnoorapps.recipefinder.ui.theme.dialogFieldColors

@Composable
fun StepsInputDialog(
    onConfirm: (Step) -> Unit,
    onDismiss: () -> Unit,
    assignedIndex: Int = 0
) {
    var index by remember { mutableIntStateOf(assignedIndex) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    androidx.compose.material3.AlertDialog(
        containerColor = Secondary,
        onDismissRequest = onDismiss,
        title = { Text(color = Color.White, text = "Add Step") },
        text = {
            Column {
                OutlinedTextField(
                    colors = dialogFieldColors(),
                    value = index.toString(),
                    onValueChange = { value ->
                        value.toIntOrNull()?.let { index = it }
                    },
                    label = { Text("Index") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                OutlinedTextField(
                    colors = dialogFieldColors(),
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )

                OutlinedTextField(
                    colors = dialogFieldColors(),
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Instructions") }
                )

                OutlinedTextField(
                    colors = dialogFieldColors(),
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image") },
                    placeholder = { Text("No function (Requires Premium Firestore)") },
                )
            }
        },
        confirmButton = {
            Button(
                enabled = title.isNotBlank() && description.isNotBlank() && index.toString().isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = Color.White
                ),
                onClick = {
                    if (title.isNotBlank() && description.isNotBlank() && index.toString().isNotBlank()) {
                        onConfirm(Step(index.toInt(), title.lowercase().replaceFirstChar(Char::titlecase), description, imageUrl))
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
fun EditStepInputDialog(
    step: Step,
    onConfirm: (Step) -> Unit,
    onDismiss: () -> Unit,
    onRemove: () -> Unit
) {
    val newIndex = remember { mutableIntStateOf(step.index) }
    val newTitle = remember { mutableStateOf(step.title) }
    val newDescription = remember { mutableStateOf(step.description) }
    val newImageUrl = remember { mutableStateOf(step.imageUrl) }

    androidx.compose.material3.AlertDialog(
        containerColor = Secondary,
        onDismissRequest = onDismiss,
        title = { Text("Edit Step", color = Color.White) },
        text = {
            Column {
                OutlinedTextField(
                    colors = dialogFieldColors(),
                    value = newIndex.intValue.toString(),
                    onValueChange = { value ->
                        value.toIntOrNull()?.let { newIndex.intValue = it }
                    },
                    label = { Text("Index") },
                    placeholder = { Text(newIndex.intValue.toString()) },
                    modifier = Modifier.fillMaxWidth(.4f),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
                OutlinedTextField(
                    colors = dialogFieldColors(),
                    value = newTitle.value,
                    onValueChange = { newTitle.value = it },
                    label = { Text("Title") },
                    placeholder = { Text(newTitle.value) },
                )
                OutlinedTextField(
                    colors = dialogFieldColors(),
                    value = newDescription.value,
                    onValueChange = { newDescription.value = it },
                    label = { Text("Description") },
                    placeholder = { Text(newDescription.value) },
                )
                OutlinedTextField(
                    colors = dialogFieldColors(),
                    value = newImageUrl.value,
                    onValueChange = { newImageUrl.value = it },
                    label = { Text("Image URL") },
                    placeholder = { Text(newImageUrl.value) },
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
                        text = "Remove Step",
                        color = Color.White,
                        modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 8.dp)
                    )
                }
            }

        },
        confirmButton = {
            Button(
                enabled = newTitle.value.isNotBlank() && newDescription.value.isNotBlank() && newIndex.intValue.toString().isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                    contentColor = Color.White
                ),
                onClick = {onConfirm(Step(newIndex.intValue, newTitle.value.lowercase().replaceFirstChar(Char::titlecase), newDescription.value, newImageUrl.value))}
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