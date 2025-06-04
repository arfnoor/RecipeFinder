package com.example.recipefinder.ui.recipes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipefinder.data.Recipe
import com.example.recipefinder.data.Tag
import com.example.recipefinder.ui.market.MarketScreen

@Composable
fun SingleRecipeScreen(
    recipe: Recipe,
) {
    // This is where you would implement the UI for displaying a single recipe.
    Text(
        text = "Recipe: ${recipe.title}\nDescription: ${recipe.description}",
        modifier = androidx.compose.ui.Modifier.padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun SingleRecipeScreenPreview() {
    val example = Recipe(1, "Example Recipe 1", "Description of Example Recipe 1", 40, tags = listOf(Tag.SOUP, Tag.LUNCH, Tag.PASTA, Tag.DAIRY_FREE, Tag.TOFU, Tag.GLUTEN_FREE))
    SingleRecipeScreen(example)
}