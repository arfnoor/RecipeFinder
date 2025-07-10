package com.example.recipefinder.data
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RecipeViewModel : ViewModel() {
    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes
    private val _savedRecipes = MutableStateFlow<List<Recipe>>(emptyList())
    val savedRecipes: StateFlow<List<Recipe>> = _savedRecipes
    fun setRecipes(newRecipes: List<Recipe>) {
        _recipes.value = newRecipes
    }
    fun setSavedRecipes(newRecipes: List<Recipe>) {
        _savedRecipes.value = newRecipes
    }
}