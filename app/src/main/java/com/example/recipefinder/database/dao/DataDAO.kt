package com.example.recipefinder.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.recipefinder.data.Recipe

@Dao
interface DataDAO {
     @Insert
     fun insertRecipe(recipe: Recipe)

     @Query("SELECT * FROM recipedto")
     fun getAllRecipes(): List<Recipe>

     @Query("SELECT * FROM recipedto WHERE title = :title")
     fun getRecipesByTitle(title: String): List<Recipe>
}