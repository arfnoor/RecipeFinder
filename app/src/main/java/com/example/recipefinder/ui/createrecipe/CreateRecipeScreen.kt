package com.example.recipefinder.ui.createrecipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipefinder.data.Ingredient
import com.example.recipefinder.data.Recipe
import com.example.recipefinder.ui.theme.Primary
import com.example.recipefinder.ui.theme.RecipeFinderTheme
import com.example.recipefinder.ui.theme.Secondary

@Composable
fun CreateRecipeScreen(
    onCreate: (Recipe) -> Unit,
) {
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
                    value = "", // Replace with state variable
                    onValueChange = { /* update state */ },
                    label = { Text("Title") },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth(.92f)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(
                    modifier = Modifier.fillMaxHeight(0.02f)
                )
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
                    value = "", // Replace with state variable
                    onValueChange = { /* update state */ },
                    label = { Text("Description") },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        unfocusedBorderColor = Color.Transparent
                    ),
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
                var ingredients by remember { mutableStateOf(listOf<Ingredient>()) }
                var showIngredientDialog by remember { mutableStateOf(false) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                )
                {
                    ingredients.forEach {
                        Box(
                            modifier = Modifier
                            .padding(8.dp)
                            .clickable { ingredients = ingredients - it }
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                        )
                        {
                            Text(
                                text = it.name,
                                color = Secondary,
                                modifier = Modifier
                                    .padding(8.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                }

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
                                id = 0, // Replace with appropriate ID logic
                                title = "", // Replace with state variable
                                description = "", // Replace with state variable
                                ingredients = listOf(), // Replace with state variable
                                steps = listOf(), // Replace with state variable,
                                preparationTime = 0, // Replace with state variable
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

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add Ingredient") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity") }
                )
                OutlinedTextField(
                    value = unit,
                    onValueChange = { unit = it },
                    label = { Text("Unit") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank() && quantity.isNotBlank() && unit.isNotBlank()) {
                        onConfirm(Ingredient(0, name.lowercase().replaceFirstChar(Char::titlecase), quantity.toInt(), com.example.recipefinder.data.Unit.valueOf(unit.uppercase())))
                    }
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
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