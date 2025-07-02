package com.example.recipefinder.ui.createrecipe

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipefinder.data.Ingredient
import com.example.recipefinder.data.Recipe
import com.example.recipefinder.data.Step
import com.example.recipefinder.data.Tag
import com.example.recipefinder.data.abbreviateUnit
import com.example.recipefinder.ui.theme.Primary
import com.example.recipefinder.ui.theme.RecipeFinderTheme
import com.example.recipefinder.ui.theme.Secondary
import com.example.recipefinder.ui.theme.inputFieldColors


@Composable
fun CreateRecipeScreen(
    onCreate: (Recipe) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf(listOf<Ingredient>()) }
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
                var showStepDialog by remember { mutableStateOf(false) }
                var selectedStep by remember { mutableStateOf<Step?>(null) }
                var showSelectedStepDialog by remember { mutableStateOf(false) }
                if (showStepDialog) {
                    StepsInputDialog(
                        onConfirm = { oldStep ->
                            val step: Step = if (oldStep.index >= steps.size + 1) {
                                oldStep.copy(index = steps.size + 1)
                            } else{
                                oldStep
                            }
                            steps = steps + step
                            var i = step.index + 1
                            val newSteps = steps.toMutableList()
                            for(j in newSteps.indices){
                                if (newSteps[j].index >= step.index && step != newSteps[j]) {
                                    newSteps[j] = newSteps[j].copy(index = i)
                                    i++
                                }
                            }
                            newSteps.sortBy { it.index }
                            steps = newSteps
                            showStepDialog = false
                        },
                        onDismiss = { showStepDialog = false },
                        assignedIndex = steps.size + 1,
                    )
                }
                if (showSelectedStepDialog && selectedStep != null) {
                    EditStepInputDialog(
                        selectedStep!!,
                        onConfirm = { oldStep ->
                            val step: Step = if (oldStep.index >= steps.size) {
                                oldStep.copy(index = steps.size)
                            } else{
                                oldStep
                            }
                            steps = steps - selectedStep!! + step
                            var i = step.index + 1
                            val newSteps = steps.toMutableList()
                            for(j in newSteps.indices){
                                if (newSteps[j].index >= step.index && step != newSteps[j]) {
                                    newSteps[j] = newSteps[j].copy(index = i)
                                    i++
                                }
                            }
                            newSteps.sortBy { it.index }
                            steps = newSteps
                            showSelectedStepDialog = false
                            selectedStep = null
                        },
                        onDismiss = { showSelectedStepDialog = false },
                        onRemove = {
                            steps = steps - selectedStep!!
                            showSelectedStepDialog = false
                            selectedStep = null
                        }
                    )
                }
                Column {
                    steps.forEach {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    selectedStep = it
                                    showSelectedStepDialog = true
                                }
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .fillMaxWidth()
                        )
                        {
                            Column {
                                Text(
                                    text = "${it.index}. ${it.title}",
                                    color = Secondary,
                                    modifier = Modifier
                                        .padding(8.dp),
                                    style = MaterialTheme.typography.titleMedium,
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = it.description,
                                    color = Secondary,
                                    modifier = Modifier
                                        .padding(8.dp),
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
                Button(
                    onClick = {
                        showStepDialog = true
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
                                imageUrl = imageUrl,
                                style = style,
                                tags = tags,
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

@Preview(showBackground = true)
@Composable
fun CreateRecipeScreenPreview() {
    RecipeFinderTheme {
        CreateRecipeScreen(onCreate = {})
    }

}