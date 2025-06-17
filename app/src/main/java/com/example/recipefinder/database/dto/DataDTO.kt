package com.example.recipefinder.database.dto

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.recipefinder.data.Ingredient
import com.example.recipefinder.data.Recipe
import com.example.recipefinder.data.Step
import com.example.recipefinder.data.Tag
import com.example.recipefinder.data.Unit

// Represents a recipe in the Recipe Finder application.
@Entity
data class RecipeDTO(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name="title") val title: String,
    @ColumnInfo(name="description") val description: String,
    @ColumnInfo(name="prep_time") val preparationTime: Int,
    @ColumnInfo(name="image_url") val imageUrl: String = "",
    @ColumnInfo(name="style") val style: String = "",
)

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RecipeDTO::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class IngredientDTO(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val recipeId: Int, // Foreign key to RecipeDTO
    val name: String,
    val quantity: Int,
    val unit: String,
    val note: String = ""
)

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RecipeDTO::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class StepDTO(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val recipeId: Int,
    val title: String,
    val stepNumber: Int,
    val instruction: String
)

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = RecipeDTO::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TagDTO(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val recipeId: Int,
    val tag: String
)

data class RecipeWithInformation(
    @Embedded val recipe: RecipeDTO,

    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val ingredients: List<IngredientDTO>,

    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val steps: List<StepDTO> = emptyList(),

    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val tags: List<TagDTO> = emptyList()
)

fun dtoToRecipe(data: RecipeWithInformation): Recipe {
    return Recipe(
        id = data.recipe.id,
        title = data.recipe.title,
        description = data.recipe.description,
        preparationTime = data.recipe.preparationTime,
        steps = data.steps.map { stepDTO ->
            Step(
                index = stepDTO.stepNumber,
                title = stepDTO.title,
                description = stepDTO.instruction,
                imageUrl = "" // Assuming no image URL for steps in this case
            )
        },
        imageUrl = data.recipe.imageUrl,
        ingredients = data.ingredients.map { ingredientDTO ->
            Ingredient(
                id = ingredientDTO.id,
                name = ingredientDTO.name,
                quantity = ingredientDTO.quantity,
                unit = Unit.valueOf(ingredientDTO.unit),
                note = ingredientDTO.note
            )
        },
        style = data.recipe.style,
        tags = data.tags.map { tagDTO ->
            Tag.valueOf(tagDTO.tag)
        }
    )
}

fun recipeToRecipeDto(recipe: Recipe): RecipeDTO {
    return RecipeDTO(
        id = recipe.id,
        title = recipe.title,
        description = recipe.description,
        preparationTime = recipe.preparationTime,
        imageUrl = recipe.imageUrl,
        style = recipe.style,
    )
}

fun recipeToIngredientDtos(recipe: Recipe): List<IngredientDTO> {
    return recipe.ingredients.map { ingredient ->
        IngredientDTO(
            name = ingredient.name,
            quantity = ingredient.quantity,
            unit = ingredient.unit.name,
            note = ingredient.note,
            recipeId = recipe.id
        )
    }
}

fun recipeToStepDtos(recipe: Recipe): List<StepDTO> {
    return recipe.steps.mapIndexed { index, step ->
        StepDTO(
            title = step.title,
            stepNumber = index + 1,
            instruction = step.description,
            recipeId = recipe.id
        )
    }
}

fun recipeToTagDtos(recipe: Recipe): List<TagDTO> {
    return recipe.tags.map { tag ->
        TagDTO(
            tag = tag.name,
            recipeId = recipe.id
        )
    }
}