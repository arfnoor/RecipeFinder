package com.adamnoorapps.recipefinder.data


// Represents a recipe in the Recipe Finder application.
data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val preparationTime: Int,
    val steps : List<Step> = emptyList(),
    val imageUrl: String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val style: String = "",
    val tags: List<Tag> = emptyList(),
    val owner: String = "Anonymous"
)

// Represents an ingredient in a recipe.
data class Ingredient(
    val id: Int,
    val name: String,
    val quantity: Int,
    val unit: Unit,
    val note: String = ""
)

// Represents a step in the preparation of a recipe.
data class Step(
    val index: Int = 0,
    val title: String = "Something went wrong",
    val description: String = "",
    val imageUrl: String = ""
)

// Represents a tag that can be associated with a recipe.
enum class Tag {
    VEGETARIAN, VEGAN, GLUTEN_FREE, DAIRY_FREE, PASTA, DESSERT, SALAD, SOUP, PROTEIN, TOFU, CURRY, SPICY, SWEET, SAVORY, BREAKFAST, LUNCH, DINNER, SNACK, SMOOTHIE
}

// Represents a unit of measurement for ingredients.
enum class Unit {
    GRAM, LITER, MILLILITER, CUP, TABLESPOON, TEASPOON, PIECE, WHOLE, DASH
}

fun abbreviateUnit(unit: Unit): String {
    return when (unit) {
        Unit.GRAM -> "g"
        Unit.LITER -> "L"
        Unit.MILLILITER -> "ml"
        Unit.CUP -> " cup"
        Unit.TABLESPOON -> "tbsp"
        Unit.TEASPOON -> "tsp"
        Unit.PIECE -> "pcs"
        Unit.WHOLE -> " whole"
        Unit.DASH -> " dash"
    }
}

// Find recipes that match tags, style, or ingredients.
fun findRecipesWithTags(
    recipes: List<Recipe>,
    tags: List<Tag>
): List<Recipe> {
    return recipes.filter { recipe ->
        recipe.tags.all { it in tags }
    }
}

fun findRecipesWithStyle(
    recipes: List<Recipe>,
    style: String
): List<Recipe> {
    return recipes.filter { recipe ->
        recipe.style == style
    }
}

fun findRecipesWithSomeIngredients(
    recipes: List<Recipe>,
    ingredients: List<String>
): List<Recipe> {
    return recipes.filter { recipe ->
        recipe.ingredients.any { ingredient ->
            ingredient.name in ingredients
        }
    }
}

fun findRecipesWithAllIngredients(
    recipes: List<Recipe>,
    ingredients: List<String>
): List<Recipe> {
    return recipes.filter { recipe ->
        ingredients.all { ingredient ->
            recipe.ingredients.any { it.name == ingredient }
        }
    }
}