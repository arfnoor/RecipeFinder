package com.example.recipefinder.ui.search

import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.data.Recipe
import com.example.recipefinder.data.Tag
import com.example.recipefinder.ui.theme.Primary
import com.example.recipefinder.ui.theme.RecipeFinderTheme
import com.example.recipefinder.ui.theme.Secondary
import com.example.recipefinder.ui.theme.Typography

@Composable
fun SearchScreen(
    recipes: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit = {}
)
{
    val searchQuery = androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("") }
    val ingredients = recipes.flatMap { it.ingredients.map { ingredient -> ingredient.name } }.distinct()
    val selectedIngredients = androidx.compose.runtime.remember {
        androidx.compose.runtime.mutableStateListOf<String>()
    }
    // Filter recipes based on search query and selected ingredients from detailed search
    var filteredRecipes = if (searchQuery.value.isBlank() && selectedIngredients.size == 0) recipes
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
    Box(
        modifier = Modifier.fillMaxSize()
    )
    {
        Column {
            Column (
                modifier = Modifier.background(color = Primary)
            ){
                androidx.compose.material3.OutlinedTextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    label = { Text("Search recipes") },
                    colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Primary,
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.Gray
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                val showDetailedSearch = androidx.compose.runtime.remember {
                    androidx.compose.runtime.mutableStateOf(false)
                }
                Text(
                    text = if (showDetailedSearch.value) "Hide Detailed Search \u25B2" else "Show Detailed Search \u25BC",
                    color = Color.White,
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
                            label = { Text("Filter ingredients", color = if (ingredientQuery.value.isNotBlank()) Color.White else Color.Gray ) },
                            keyboardOptions = KeyboardOptions(autoCorrectEnabled = false),
                            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedBorderColor = Primary,
                                unfocusedBorderColor = Color.Gray,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black
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
                                            checkedColor = Color.White,
                                            checkmarkColor = Primary,
                                            uncheckedColor = Color.White,
                                            disabledCheckedColor = Color.White,
                                            disabledUncheckedColor = Color.Transparent,
                                            disabledIndeterminateColor = Color.White
                                        ),
                                    )
                                    Text(
                                        text = ingredient,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }

                }
            }
            // Display recipes in a grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Iterate through filtered recipes and display each recipe in a card
                items(filteredRecipes) { recipe ->
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        tonalElevation = 2.dp,
                        color = Primary.copy(alpha = 0.85f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(0.9f)
                            .clickable {  onRecipeClick(recipe) }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            Image(
                                painter = androidx.compose.ui.res.painterResource(id = com.example.recipefinder.R.drawable.alfredo),
                                contentDescription = recipe.title,
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop,
                                alpha = 0.5f
                            )
                            Column(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // The title of the recipe
                                Text(
                                    text = recipe.title,
                                    style = Typography.titleSmall.copy(
                                        shadow = Shadow(
                                            color = Color.Black,
                                            offset = Offset(0.0f, 0.0f),
                                            blurRadius = 15f
                                        )
                                    ),
                                    textAlign = TextAlign.Center,
                                    maxLines = 2,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(28.dp))
                                Row {
                                    Icon(
                                        imageVector = androidx.compose.material.icons.Icons.Outlined.Create,
                                        contentDescription = "Time to cook",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .padding(end = 4.dp)
                                            .size(18.dp)
                                            .then(
                                                Modifier
                                                    .graphicsLayer {
                                                        shadowElevation = 8f
                                                        clip = false
                                                    }
                                            )
                                    )
                                    Text(
                                        text = "${recipe.preparationTime} min",
                                        style = Typography.labelLarge.copy(
                                            shadow = Shadow(
                                                color = Color.Black,
                                                offset = Offset(0.0f, 0.0f),
                                                blurRadius = 5f
                                            )
                                        ),
                                        color = Color.White
                                    )
                                }
                                Spacer(modifier = Modifier.height(3.dp))
                                Row {
                                    recipe.tags.take(2).forEach { tag ->
                                        Box(
                                            modifier = Modifier.background(color = Secondary)
                                                .padding(horizontal = 2.dp),

                                            )
                                        {
                                            Text(
                                                text = tag.name.replace('_', ' ').lowercase()
                                                    .replaceFirstChar { it.uppercase() },

                                                color = Color.White,
                                                fontSize = 12.sp
                                            )
                                        }
                                        Spacer(
                                            modifier = Modifier.width(4.dp)
                                        )

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    val example: MutableList<Recipe>  = mutableListOf(
        Recipe(1, "Example Recipe 1",  "Description of Example Recipe 1", 40, tags = listOf(Tag.SOUP, Tag.LUNCH, Tag.PASTA, Tag.DAIRY_FREE, Tag.TOFU, Tag.GLUTEN_FREE)),
        Recipe(2, "Example Recipe 2", "Description of Example Recipe 2", 80, tags = listOf(Tag.BREAKFAST, Tag.VEGAN)),
        Recipe(3, "Example Recipe 3", "Description of Example Recipe 3", 120, tags = listOf(Tag.DESSERT)),
        Recipe(4, "Example Recipe 4", "Description of Example Recipe 4", 20),
        Recipe(5, "Example Recipe 5", "Description of Example Recipe 5", 15),
    )
    RecipeFinderTheme {
        SearchScreen(example)
    }

}