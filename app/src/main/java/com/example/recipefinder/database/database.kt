package com.example.recipefinder.database

import com.example.recipefinder.data.Ingredient
import com.example.recipefinder.data.Recipe
import com.example.recipefinder.data.Step
import com.example.recipefinder.data.Tag
import com.example.recipefinder.data.Unit
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

val database = Firebase.database.reference

data class SafeRecipe(
    val id: Int,
    val title: String,
    val description: String,
    val preparationTime: Int,
    val steps : List<Step> = emptyList(),
    val imageUrl: String = "",
    val ingredients: List<SafeIngredient> = emptyList(),
    val style: String = "",
    val tags: List<SafeTag> = emptyList(),
    val owner: String
)

data class SafeIngredient(
    val id: Int,
    val name: String,
    val quantity: Int,
    val unit: String,
    val note: String = ""
)

data class SafeTag(
    val tag: String
)

/**
 * Convert a SafeRecipe (database object) into a Recipe
 */
fun safeRecipeConverter(recipe: SafeRecipe): Recipe
{
    return Recipe(
        id = recipe.id,
        title = recipe.title,
        description = recipe.description,
        preparationTime = recipe.preparationTime,
        steps = recipe.steps,
        imageUrl = recipe.imageUrl,
        ingredients = recipe.ingredients.map { ingredient ->
            Ingredient(
                id = ingredient.id,
                name = ingredient.name,
                quantity = ingredient.quantity,
                unit = Unit.valueOf(ingredient.unit), // Convert String to enum
                note = ingredient.note
            )
        },
        style = recipe.style,
        tags = recipe.tags.map { tag ->
            Tag.valueOf(tag.tag) // Convert String to enum
        }
    )
}

/**
 * Convert a Recipe into a SafeRecipe (database object)
 */
fun recipeConverter(recipe: Recipe): SafeRecipe
{
    return SafeRecipe(
        id = recipe.id,
        title = recipe.title,
        description = recipe.description,
        preparationTime = recipe.preparationTime,
        steps = recipe.steps,
        imageUrl = recipe.imageUrl,
        ingredients = recipe.ingredients.map { ingredient ->
            SafeIngredient(
                id = ingredient.id,
                name = ingredient.name,
                quantity = ingredient.quantity,
                unit = ingredient.unit.name, // Convert enum to String
                note = ingredient.note
            )
        },
        style = recipe.style,
        tags = recipe.tags.map { tag ->
            SafeTag(tag.name) // Convert enum to String
        },
        owner = recipe.owner
    )
}

fun writeRecipeToDatabase(recipe: Recipe) {
    database.child("recipes").child(recipe.id.toString()).setValue(recipeConverter(recipe))
//        .addOnSuccessListener {
//            // Write was successful
//            println("Recipe written successfully: ${recipe.title}")
//        }
//        .addOnFailureListener { exception ->
//            // Write failed
//            println("Error writing recipe: ${exception.message}")
//        }
}

fun readRecipesFromDatabase(onSuccess: (List<Recipe>) -> Unit, onFailure: (Exception) -> Unit) {
    database.child("recipes").get()
        .addOnSuccessListener { dataSnapshot ->
            // Read was successful
            val recipes = dataSnapshot.children.mapNotNull { it.getValue(SafeRecipe::class.java)
                ?.let { it1 -> safeRecipeConverter(it1) } }
            onSuccess(recipes)
        }
        .addOnFailureListener { exception ->
            // Read failed
            onFailure(exception)
        }
}

fun updateRecipeInDatabase(recipe: Recipe) {
    database.child("recipes").child(recipe.id.toString()).setValue(recipeConverter(recipe))
//        .addOnSuccessListener {
//            // Update was successful
//            println("Recipe updated successfully: ${recipe.title}")
//        }
//        .addOnFailureListener { exception ->
//            // Update failed
//            println("Error updating recipe: ${exception.message}")
//        }
}