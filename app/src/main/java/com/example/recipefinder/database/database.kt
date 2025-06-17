package com.example.recipefinder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipefinder.database.dao.DataDAO
import com.example.recipefinder.data.Recipe

@Database(version = 1, entities = [Recipe::class])
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): DataDAO

}