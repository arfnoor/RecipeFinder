package com.adamnoorapps.recipefinder.database

import com.adamnoorapps.recipefinder.data.Ingredient
import com.adamnoorapps.recipefinder.data.Recipe
import com.adamnoorapps.recipefinder.data.Step
import com.adamnoorapps.recipefinder.data.Tag
import com.adamnoorapps.recipefinder.data.Unit
import com.google.firebase.firestore.FirebaseFirestore


data class SafeRecipe(
    val id: Int = 0,
    val title: String = "Something went wrong",
    val description: String = "",
    val preparationTime: Int = 0,
    val steps : List<Step> = emptyList(),
    val imageUrl: String = "",
    val ingredients: List<SafeIngredient> = emptyList(),
    val style: String = "",
    val tags: List<SafeTag> = emptyList(),
    val owner: String = "Anonymous"
)

data class SafeIngredient(
    val id: Int = 0,
    val name: String = "Something went wrong",
    val quantity: Int = 0,
    val unit: String = "",
    val note: String = ""
)

data class SafeTag(
    val tag: String = ""
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

fun writeRecipeToDatabase(db: FirebaseFirestore, recipe: Recipe) {
    val newDocRef = db.collection("RECIPES").document()
    newDocRef.set(recipeConverter(recipe.copy(id = newDocRef.id.hashCode())))
        .addOnSuccessListener {
            // Write was successful
            println("Recipe written successfully: ${recipe.title}")
        }
        .addOnFailureListener { exception ->
            // Write failed
            println("Error writing recipe: ${exception.message}")
        }
}


fun readRecipesFromDatabase(db: FirebaseFirestore, onSuccess: (List<Recipe>) -> kotlin.Unit, onFailure: (Exception) -> kotlin.Unit) {
    db.collection("RECIPES").get()
        .addOnSuccessListener { dataSnapshot ->
            // Read was successful
            val recipes = dataSnapshot.documents.mapNotNull { document ->
                val safeRecipe = document.toObject(SafeRecipe::class.java)
                safeRecipe?.let { safeRecipeConverter(it) }
            }
            onSuccess(recipes)
        }
        .addOnFailureListener { exception ->
            // Read failed
            onFailure(exception)
        }
}

fun readSavedRecipesFromDatabase(db: FirebaseFirestore, recipes: List<Recipe>, userId: String, onSuccess: (List<Recipe>) -> kotlin.Unit) {
    db.collection("USERS").document(userId).get()
        .addOnSuccessListener { document ->
            if (document.exists()) {
                val savedRecipeIds = document.get("savedRecipes") as? List<String> ?: emptyList()
                val savedRecipes = recipes.filter { it.id.toString() in savedRecipeIds }
                onSuccess(savedRecipes)
            } else {
                // User document does not exist, return empty list
                onSuccess(emptyList())
            }
        }
        .addOnFailureListener { exception ->
            // Read failed
            println("Error reading saved recipes: ${exception.message}")
            onSuccess(emptyList())
        }
}

fun updateSavedRecipesInDatabase(db: FirebaseFirestore, userId: String, savedRecipes: List<Recipe>) {
    val savedRecipeIds = savedRecipes.map { it.id.toString() }
    db.collection("USERS").document(userId).set(mapOf("savedRecipes" to savedRecipeIds))
        .addOnSuccessListener {
            // Update was successful
            println("Saved recipes updated successfully for user: $userId")
        }
        .addOnFailureListener { exception ->
            // Update failed
            println("Error updating saved recipes: ${exception.message}")
        }
}

fun updateRecipeInDatabase(db: FirebaseFirestore, recipe: Recipe) {
    db.collection("RECIPES").document(recipe.id.toString()).set(recipeConverter(recipe))
//        .addOnSuccessListener {
//            // Update was successful
//            println("Recipe updated successfully: ${recipe.title}")
//        }
//        .addOnFailureListener { exception ->
//            // Update failed
//            println("Error updating recipe: ${exception.message}")
//        }
}