package com.example.recipefinder.data
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf

class RecipeViewModel : ViewModel() {
    val recipes = mutableStateListOf<Recipe>()
    fun setRecipes(newRecipes: List<Recipe>) {
        recipes.clear()
        recipes.addAll(newRecipes)
    }
}