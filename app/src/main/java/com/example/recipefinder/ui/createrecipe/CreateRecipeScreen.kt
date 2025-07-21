package com.example.recipefinder.ui.createrecipe

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.recipefinder.data.Ingredient
import com.example.recipefinder.data.Recipe
import com.example.recipefinder.data.RecipeViewModel
import com.example.recipefinder.data.Step
import com.example.recipefinder.data.Tag
import com.example.recipefinder.data.abbreviateUnit
import com.example.recipefinder.ui.recipes.SingleRecipeScreen
import com.example.recipefinder.ui.theme.Primary
import com.example.recipefinder.ui.theme.RecipeFinderTheme
import com.example.recipefinder.ui.theme.Secondary
import com.example.recipefinder.ui.theme.Tertiary
import com.example.recipefinder.ui.theme.inputFieldColors
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun CreateRecipeScreen(
    onCreate: (Recipe) -> Unit,
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var ingredients by remember { mutableStateOf(listOf<Ingredient>()) }
    var preparationTime by remember { mutableIntStateOf(0) }
    var steps by remember { mutableStateOf(listOf<Step>()) }
    val imageUrl by remember { mutableStateOf("") }
    var style by remember { mutableStateOf("") }
    var tags by remember { mutableStateOf(listOf<Tag>()) }
    var showPreview by remember { mutableStateOf(false) }
    Box (
        modifier = Modifier.fillMaxSize().zIndex(1f)
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
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
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
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                // Recipe Title
                Text(
                    text = "Recipe Title*",
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
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                // Recipe Description
                Text(
                    text = "Recipe Description*",
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
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Row (
                    modifier = Modifier.fillMaxWidth(.96f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth(.45f)
                            .padding(start = 16.dp)

                    ) {
                        Text(
                            text = "Prep Time*",
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.titleSmall.copy(
                                shadow = Shadow(
                                    color = Secondary,
                                    offset = Offset(0.0f, 0.0f),
                                    blurRadius = 15f,
                                )
                            ),
                        )
                        Row(

                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = if (preparationTime == 0) "" else preparationTime.toString(),
                                onValueChange = { value ->
                                    preparationTime = value.toIntOrNull() ?: 0
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                ),
                                colors = inputFieldColors(),
                                modifier = Modifier
                                    .fillMaxWidth(.5f)
                            )
                            Text(
                                text = "minutes",
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 4.dp),
                                fontSize = 16.sp,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    shadow = Shadow(
                                        color = Secondary,
                                        offset = Offset(0.0f, 0.0f),
                                        blurRadius = 15f,
                                    )
                                )
                            )
                        }

                    }
                    Column{
                        Text(
                            text = "Recipe Style",
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxWidth()
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
                            value = style,
                            onValueChange = { style = it },
                            colors = inputFieldColors(),
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
                Spacer(
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "Recipe Ingredients*",
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
                    text = "Recipe Steps*",
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
                                )
                                Text(
                                    text = it.description,
                                    color = Secondary,
                                    modifier = Modifier
                                        .padding(8.dp),
                                    style = MaterialTheme.typography.bodyMedium,
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
                Text(
                    text = "Recipe Tags",
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
                FlowRow(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
                {
                    tags.forEach {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .background(Color.White, shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    tags = tags - it
                                }
                        )
                        {
                            Text(
                                text = it.name.lowercase().replaceFirstChar(Char::titlecase).replace("_", " "),
                                color = Secondary,
                                modifier = Modifier
                                    .padding(8.dp),
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                val showTagMenu = remember { mutableStateOf(false) }
                Button(
                    onClick = {
                        showTagMenu.value = true
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
                Box (
                    modifier = Modifier
                        .fillMaxWidth(.5f)

                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp)
                ) {
                    DropdownMenu(
                        expanded = showTagMenu.value,
                        onDismissRequest = { showTagMenu.value = false },
                        modifier = Modifier
                            .fillMaxHeight(.3f)
                            .fillMaxWidth(.4f)

                    ) {
                        var tagSearch by remember { mutableStateOf("") }
                        OutlinedTextField(
                            value = tagSearch,
                            onValueChange = { tagSearch = it },
                            placeholder = { Text("Search tags...") },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                        Tag.entries
                            .filter { it.name.contains(tagSearch, ignoreCase = true) }
                            .forEach { tag ->
                                DropdownMenuItem(
                                    text = { Text(tag.name.lowercase().replaceFirstChar(Char::titlecase).replace("_", " "), fontSize = 20.sp) },
                                    onClick = {
                                        tags = tags + tag
                                    }
                                )
                            }
                    }
                }

                Button(
                    onClick = { showPreview = true },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Tertiary,
                        contentColor = Color.White
                    ),
                    enabled = title.isNotBlank() && description.isNotBlank() && ingredients.isNotEmpty() && steps.isNotEmpty() && preparationTime > 0
                ) {
                    Text("Preview Recipe", color = Secondary)
                }

                Spacer(
                    modifier = Modifier.fillMaxHeight(0.02f)
                )
                Button(
                    enabled = title.isNotBlank() && description.isNotBlank() && ingredients.isNotEmpty() && steps.isNotEmpty() && preparationTime > 0,
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
                                imageUrl = "",
                                style = style,
                                tags = tags,
                                owner = Firebase.auth.currentUser?.uid ?: "Anonymous"
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
    AnimatedVisibility(
        visible = showPreview,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = Modifier.zIndex(2f)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .zIndex(2f)


        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .fillMaxHeight(.9f)
                    .padding(16.dp)
            ) {
                Column {
                    // Close button
                    Button(
                        onClick = { showPreview = false },
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Secondary,
                            contentColor = Color.White
                        ),
                    ) {
                        Text("Close Preview", color = Color.White)
                    }
                    // Preview content
                    SingleRecipeScreen(
                        recipe = Recipe(
                            id = 0,
                            title = title,
                            description = description,
                            ingredients = ingredients,
                            steps = steps,
                            preparationTime = preparationTime,
                            imageUrl = imageUrl,
                            style = style,
                            tags = tags,
                        ),
                        model = RecipeViewModel()
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