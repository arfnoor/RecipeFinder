package com.example.recipefinder.ui.market

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipefinder.data.Recipe
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.recipefinder.data.Tag
import com.example.recipefinder.ui.theme.Primary
import com.example.recipefinder.ui.theme.RecipeFinderTheme
import com.example.recipefinder.ui.theme.Secondary
import com.example.recipefinder.ui.theme.Typography

@Composable
fun MarketScreen(
    recipes: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit = {}
)
{
    val searchQuery = androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf("") }
    // Filter recipes based on search query, if search query is empty, show all recipes
    val filteredRecipes = if (searchQuery.value.isBlank()) recipes else recipes.filter {
        it.title.contains(searchQuery.value, ignoreCase = true) ||
            it.description.contains(searchQuery.value, ignoreCase = true)
    }
    Column {
        // Search through recipes
        androidx.compose.material3.OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            label = { Text("Search recipes") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
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
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold
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

@Preview(showBackground = true)
@Composable
fun MarketScreenPreview() {
    val example: MutableList<Recipe>  = mutableListOf(
        Recipe(1, "Example Recipe 1",  "Description of Example Recipe 1", 40, tags = listOf(Tag.SOUP, Tag.LUNCH, Tag.PASTA, Tag.DAIRY_FREE, Tag.TOFU, Tag.GLUTEN_FREE)),
        Recipe(2, "Example Recipe 2", "Description of Example Recipe 2", 80, tags = listOf(Tag.BREAKFAST, Tag.VEGAN)),
        Recipe(3, "Example Recipe 3", "Description of Example Recipe 3", 120, tags = listOf(Tag.DESSERT)),
        Recipe(4, "Example Recipe 4", "Description of Example Recipe 4", 20),
        Recipe(5, "Example Recipe 5", "Description of Example Recipe 5", 15),
    )
    RecipeFinderTheme {
        MarketScreen(example)
    }

}