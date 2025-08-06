package com.example.recipefinder.ui.saved

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.data.Recipe
import com.example.recipefinder.data.Tag
import com.example.recipefinder.ui.theme.Primary
import com.example.recipefinder.ui.theme.RecipeFinderTheme

@Composable
fun SavedScreen(
    recipes: List<Recipe> = emptyList(),
    onRecipeClick: (Recipe) -> Unit = {}
)
{
    val searchQuery = androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("") }
    val ingredients = recipes.flatMap { it.ingredients.map { ingredient -> ingredient.name } }.distinct()
    val selectedIngredients = androidx.compose.runtime.remember {
        androidx.compose.runtime.mutableStateListOf<String>()
    }
    // Filter recipes based on search query and selected ingredients from detailed search
    val filteredRecipes = if (searchQuery.value.isBlank() && selectedIngredients.size == 0) recipes
    else if( searchQuery.value.isBlank()) recipes.filter { recipe ->
        selectedIngredients.all { selected -> recipe.ingredients.any { ingredient -> ingredient.name == selected } }
    }
    else if (selectedIngredients.size == 0)
        recipes.filter { it.title.contains(searchQuery.value, ignoreCase = true) ||
            it.description.contains(searchQuery.value, ignoreCase = true) }
    else recipes.filter {
        (it.title.contains(searchQuery.value, ignoreCase = true) ||
            it.description.contains(searchQuery.value, ignoreCase = true)) &&
            selectedIngredients.all { selected -> it.ingredients.any { ingredient -> ingredient.name == selected } }
    }
    Column {
        Column (
            modifier = Modifier.background(color = Primary)
        ){
            androidx.compose.material3.OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                    unfocusedContainerColor = androidx.compose.ui.graphics.Color.White,
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = androidx.compose.ui.graphics.Color.White,
                    focusedTextColor = androidx.compose.ui.graphics.Color.Black,
                    unfocusedTextColor = androidx.compose.ui.graphics.Color.Black,
                    focusedLabelColor = androidx.compose.ui.graphics.Color.White,
                    unfocusedLabelColor = androidx.compose.ui.graphics.Color.Gray
                ),
                label = { Text("Search recipes")},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            val showDetailedSearch = androidx.compose.runtime.remember {
                androidx.compose.runtime.mutableStateOf(false)
            }
            Text(
                text = if (showDetailedSearch.value) "Hide Detailed Search \u25B2" else "Show Detailed Search \u25BC",
                color = androidx.compose.ui.graphics.Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 12.dp, top = 4.dp, bottom = 8.dp)
                    .clickable { showDetailedSearch.value = !showDetailedSearch.value }
            )

            androidx.compose.animation.AnimatedVisibility(
                visible = showDetailedSearch.value,
                enter = androidx.compose.animation.expandVertically(),
                exit = androidx.compose.animation.shrinkVertically()
            ) {
                // Detailed search options can be added here
                Column {
                    val ingredientQuery = androidx.compose.runtime.remember {
                        androidx.compose.runtime.mutableStateOf("")
                    }
                    androidx.compose.material3.OutlinedTextField(
                        value = ingredientQuery.value,
                        onValueChange = { ingredientQuery.value = it },
                        label = { Text("Filter ingredients", color = if (ingredientQuery.value.isNotBlank()) androidx.compose.ui.graphics.Color.White else androidx.compose.ui.graphics.Color.Gray ) },
                        keyboardOptions = KeyboardOptions(autoCorrectEnabled = false),
                        colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = androidx.compose.ui.graphics.Color.White,
                            unfocusedContainerColor = androidx.compose.ui.graphics.Color.White,
                            focusedBorderColor = Primary,
                            unfocusedBorderColor = androidx.compose.ui.graphics.Color.Gray,
                            focusedTextColor = androidx.compose.ui.graphics.Color.Black,
                            unfocusedTextColor = androidx.compose.ui.graphics.Color.Black
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                    val filteredIngredients = if (ingredientQuery.value.isBlank()) ingredients
                    else ingredients.filter {
                        it.contains(
                            ingredientQuery.value,
                            ignoreCase = true
                        )
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        items(filteredIngredients.size) { index ->
                            val ingredient = filteredIngredients[index]
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(4.dp)
                            ) {
                                Checkbox(
                                    checked = selectedIngredients.contains(ingredient),
                                    onCheckedChange = { checked ->
                                        if (checked) selectedIngredients.add(ingredient)
                                        else selectedIngredients.remove(ingredient)
                                    },
                                    colors = androidx.compose.material3.CheckboxDefaults.colors(
                                        checkedColor = androidx.compose.ui.graphics.Color.White,
                                        checkmarkColor = Primary,
                                        uncheckedColor = androidx.compose.ui.graphics.Color.White,
                                        disabledCheckedColor = androidx.compose.ui.graphics.Color.White,
                                        disabledUncheckedColor = androidx.compose.ui.graphics.Color.Transparent,
                                        disabledIndeterminateColor = androidx.compose.ui.graphics.Color.White
                                    ),
                                )
                                Text(
                                    text = ingredient,
                                    color = androidx.compose.ui.graphics.Color.White,
                                    maxLines = 2,
                                    textAlign = TextAlign.Center,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(androidx.compose.ui.graphics.Color.White)
        ){
            itemsIndexed(filteredRecipes) { index, recipe ->
                val backgroundColor = if (index % 2 == 0) Primary else androidx.compose.ui.graphics.Color.White
                val contentColor = if (index % 2 == 0) androidx.compose.ui.graphics.Color.White else Primary

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(5.dp, shape = MaterialTheme.shapes.medium, ambientColor = androidx.compose.ui.graphics.Color.Black, spotColor = androidx.compose.ui.graphics.Color.Black)
                        .background(backgroundColor, shape = MaterialTheme.shapes.medium)
                        .clickable { onRecipeClick(recipe) }
                        .padding(8.dp),
                ) {
                    // Placeholder for image
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(contentColor.copy(alpha = 0.1f), shape = MaterialTheme.shapes.small)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = recipe.title,
                            color = contentColor,
                            style = MaterialTheme.typography.titleMedium
                        )
                        if (recipe.tags.isNotEmpty()) {
                            val tags: List<Tag> = recipe.tags.take(3) // Limit to 3 tags for display
                            Row {
                                tags.forEach { tag ->
                                    Text(
                                        text = tag.name.replace("_", " "),
                                        color = backgroundColor,
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier
                                            .padding(end = 6.dp)
                                            .background(
                                                color = contentColor.copy(alpha = 0.9f),
                                                shape = MaterialTheme.shapes.extraSmall
                                            )
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                        val descLines: String = recipe.description
                        Text(
                            text = descLines,
                            color = contentColor.copy(alpha = 0.85f),
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 3
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SavedScreenPreview() {
    val example: MutableList<Recipe>  = mutableListOf(
        Recipe(1, "Example Recipe 1",  "Description of Example Recipe 1", 40, tags = listOf(Tag.SOUP, Tag.LUNCH, Tag.PASTA, Tag.DAIRY_FREE, Tag.TOFU, Tag.GLUTEN_FREE)),
        Recipe(2, "Example Recipe 2", "Description of Example Recipe 2", 80, tags = listOf(Tag.BREAKFAST, Tag.VEGAN)),
        Recipe(3, "Example Recipe 3", "Description of Example Recipe 3", 120, tags = listOf(Tag.DESSERT)),
        Recipe(4, "Example Recipe 4", "Description of Example Recipe 4", 20),
        Recipe(5, "Example Recipe 5", "Description of Example Recipe 5", 15),
    )
    RecipeFinderTheme {
        SavedScreen(example, {})
    }

}