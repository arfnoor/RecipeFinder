package com.example.recipefinder.ui.createrecipe

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.data.Ingredient
import com.example.recipefinder.data.Recipe
import com.example.recipefinder.data.Step
import com.example.recipefinder.data.Tag
import com.example.recipefinder.data.abbreviateUnit
import com.example.recipefinder.ui.theme.Primary
import com.example.recipefinder.ui.theme.RecipeFinderTheme
import com.example.recipefinder.ui.theme.Secondary
import com.example.recipefinder.ui.theme.dialogFieldColors
import com.example.recipefinder.ui.theme.inputFieldColors


@Composable
fun CreateRecipeScreen(
    onCreate: (Recipe) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf(listOf<Ingredient>()) }
    var note by remember { mutableStateOf("") }
    var preparationTime by remember { mutableIntStateOf(0) }
    var steps by remember { mutableStateOf(listOf<Step>()) }
    var imageUrl by remember { mutableStateOf("") }
    var style by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf(listOf<Tag>()) }
    Box (
        modifier = Modifier.fillMaxSize()
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth(.98f)
                .align(Alignment.TopCenter)
                .padding(top = 10.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Primary)

        )
        {
            Column(
                modifier = Modifier.fillMaxWidth()
            )
            {
                //Title and description
                Text(
                    text = "Create a Recipe",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        shadow = Shadow(
                            color = Secondary,
                            offset = Offset(0.0f, 0.0f),
                            blurRadius = 15f,
                        )
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Fill in the details below to create your recipe.",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        shadow = Shadow(
                            color = Secondary,
                            offset = Offset(0.0f, 0.0f),
                            blurRadius = 15f,
                        )
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(
                    modifier = Modifier.fillMaxHeight(0.03f)
                )
                // Recipe Title
                Text(
                    text = "Recipe Title",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleSmall.copy(
                        shadow = Shadow(
                            color = Secondary,
                            offset = Offset(0.0f, 0.0f),
                            blurRadius = 15f,
                        )
                    ),
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    colors = inputFieldColors(),
                    modifier = Modifier
                        .fillMaxWidth(.92f)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(
                    modifier = Modifier.fillMaxHeight(0.02f)
                )
                // Recipe Description
                Text(
                    text = "Recipe Description",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleSmall.copy(
                        shadow = Shadow(
                            color = Secondary,
                            offset = Offset(0.0f, 0.0f),
                            blurRadius = 15f,
                        )
                    ),
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    colors = inputFieldColors(),
                    modifier = Modifier
                        .fillMaxWidth(.92f)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(
                    modifier = Modifier.fillMaxHeight(0.02f)
                )
                Text(
                    text = "Recipe Ingredients",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleSmall.copy(
                        shadow = Shadow(
                            color = Secondary,
                            offset = Offset(0.0f, 0.0f),
                            blurRadius = 15f,
                        )
                    ),
                )
                // Recipe Ingredients
                var showIngredientDialog by remember { mutableStateOf(false) }
                var selectedIngredient by remember { mutableStateOf<Ingredient?>(null) }
                var showSelectedIngredientDialog by remember { mutableStateOf(false) }
                FlowRow(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                {
                    ingredients.forEach {
                        Box(
                            modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                selectedIngredient = it
                                showSelectedIngredientDialog = true
                            }
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                        )
                        {
                            Text(
                                text = "${it.quantity}${abbreviateUnit(it.unit)} ${it.name}",
                                color = Secondary,
                                modifier = Modifier
                                    .padding(8.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                }
                // Add Ingredient Button and Functionality + Edit Ingredient Button and Functionality
                Button(
                    onClick = {
                        showIngredientDialog = true

                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 4.dp, bottom = 4.dp),
                    shape = RoundedCornerShape(100),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Secondary,
                        contentColor = Color.White
                    ),
                ) {
                    Text(text = "+", style = MaterialTheme.typography.titleMedium)
                }
                if (showIngredientDialog) {
                    IngredientInputDialog(
                        onConfirm = { ingredient ->
                            ingredients = ingredients + ingredient
                            showIngredientDialog = false
                        },
                        onDismiss = { showIngredientDialog = false }
                    )
                }
                if (showSelectedIngredientDialog && selectedIngredient != null) {
                    EditIngredientInputDialog(
                        selectedIngredient!!,
                        onConfirm = { ingredient ->
                            ingredients = ingredients - selectedIngredient!! + ingredient
                            showSelectedIngredientDialog = false
                            selectedIngredient = null
                        },
                        onDismiss = { showSelectedIngredientDialog = false },
                        onRemove = {
                            ingredients = ingredients - selectedIngredient!!
                            showSelectedIngredientDialog = false
                            selectedIngredient = null
                        }
                    )
                }
                Spacer(
                    modifier = Modifier.fillMaxHeight(0.02f)
                )
                Text(
                    text = "Recipe Steps",
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleSmall.copy(
                        shadow = Shadow(
                            color = Secondary,
                            offset = Offset(0.0f, 0.0f),
                            blurRadius = 15f,
                            )
                    ),
                )
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 4.dp, bottom = 4.dp),
                    shape = RoundedCornerShape(100),
                    contentPadding = PaddingValues(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Secondary,
                        contentColor = Color.White
                    ),
                ) {
                    Text(text = "+", style = MaterialTheme.typography.titleMedium)
                }
                Spacer(
                    modifier = Modifier.fillMaxHeight(0.02f)
                )
                Button(
                    onClick = {
                        // Handle create recipe action
                        onCreate(
                            Recipe(
                                id = 0,
                                title = title,
                                description = description,
                                ingredients = ingredients,
                                steps = steps,
                                preparationTime = preparationTime,
                            )
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Secondary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth(.5f)
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp),
                ){
                    Text(
                        text = "Create Recipe",
                        style = MaterialTheme.typography.titleSmall.copy(
                            shadow = Shadow(
                                color = Secondary,
                                offset = Offset(0.0f, 0.0f),
                                blurRadius = 15f,
                            )
                        ),
                        color = Color.White
                    )
                }
            }
        }
    }

}

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

@Preview(showBackground = true)
@Composable
fun CreateRecipeScreenPreview() {
    RecipeFinderTheme {
        CreateRecipeScreen(onCreate = {})
    }

}