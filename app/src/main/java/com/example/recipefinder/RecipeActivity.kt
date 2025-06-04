package com.example.recipefinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipefinder.data.Ingredient
import com.example.recipefinder.data.Recipe
import com.example.recipefinder.data.Tag
import com.example.recipefinder.data.Unit
import com.example.recipefinder.ui.components.RecipeFinderTabRow
import com.example.recipefinder.ui.home.HomeScreen
import com.example.recipefinder.ui.market.MarketScreen
import com.example.recipefinder.ui.recipes.SingleRecipeScreen
import com.example.recipefinder.ui.saved.SavedScreen
import com.example.recipefinder.ui.search.SearchScreen
import com.example.recipefinder.ui.settings.SettingsScreen
import com.example.recipefinder.ui.theme.RecipeFinderTheme

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecipeFinderApp()
        }
    }
}

val tomato = Ingredient(id = 1, name = "Tomato", quantity = 1, unit = Unit.CUP)
val tofu = Ingredient(id = 2, name = "Tofu", quantity = 200, unit = Unit.GRAM)
val tomatoSauce = Ingredient(id = 3, name = "Tomato Sauce", quantity = 1, unit = Unit.CUP)
val pasta = Ingredient(id = 4, name = "Pasta", quantity = 200, unit = Unit.GRAM)
val salt = Ingredient(id = 5, name = "Salt", quantity = 1, unit = Unit.TEASPOON)
val pepper = Ingredient(id = 6, name = "Pepper", quantity = 1, unit = Unit.TEASPOON)

val example: MutableList<Recipe>  = mutableListOf(
    Recipe(1, "Example Recipe 1", "Description of Example Recipe 1", 80, tags = listOf(Tag.SOUP, Tag.LUNCH, Tag.PASTA, Tag.DAIRY_FREE, Tag.TOFU, Tag.GLUTEN_FREE), ingredients = listOf(tofu, tomato, pasta)),
    Recipe(2, "Example Recipe 2", "Description of Example Recipe 2", 40, tags = listOf(Tag.BREAKFAST, Tag.VEGAN), ingredients = listOf(tomatoSauce, pepper)),
    Recipe(3, "Example Recipe 3", "Description of Example Recipe 3", 120, tags = listOf(Tag.DESSERT), ingredients = listOf(salt, pepper)),
    Recipe(4, "Example Recipe 4", "Description of Example Recipe 4", 60),
    Recipe(5, "Example Recipe 5", "Description of Example Recipe 5", 15),

)

@Composable
fun RecipeFinderApp() {
    RecipeFinderTheme {
        // Get the NavController for navigation and the current backstack entry
        val navController = rememberNavController()
        val currentBackstack = navController.currentBackStackEntryAsState()
        // Fetch current destination
        val currentDestination = currentBackstack.value?.destination
        // Default to SingleRecipe if no current destination is found (making the tab row not highlight any)
        val currentScreen = recipeDestinations.find{it.route == currentDestination?.route} ?: SingleRecipe
        Scaffold(
            bottomBar = {
                RecipeFinderTabRow(
                    allScreens = recipeDestinations,
                    onTabSelected = { destination ->
                        // Navigate to the selected destination
                        navController.navigateSingleTopTo(destination.route)
                    },
                    currentScreen = currentScreen
                )
            },
            modifier = Modifier.fillMaxSize(),

        )
        { innerPadding ->
            // Main content of the app goes here
            NavHost(
                navController = navController,
                startDestination = Home.route,
                Modifier.padding(innerPadding)) {
                composable(route = Home.route) {
                    HomeScreen(
                        {navController.navigateSingleTopTo(Market.route)},
                        {navController.navigateSingleTopTo(Saved.route)}
                    )
                }
                composable(route = Saved.route) {
                    SavedScreen(example, onRecipeClick = {recipe ->
                        // Handle recipe click, navigate to a detailed view
                        navController.navigateSingleTopTo("${SingleRecipe.route}/${recipe.id}")
                    })
                }
                composable(route = Search.route) {
                    SearchScreen(example, onRecipeClick = {recipe ->
                    // Handle recipe click, navigate to a detailed view
                    navController.navigateSingleTopTo("${SingleRecipe.route}/${recipe.id}")
                    })
                }
                composable(route = Market.route) {
                    MarketScreen(example, onRecipeClick = { recipe ->
                        // Handle recipe click, navigate to a detailed view
                        navController.navigateSingleTopTo("${SingleRecipe.route}/${recipe.id}")
                    })
                }
                composable(route = Settings.route) {
                    SettingsScreen()
                }
                composable(
                    route = SingleRecipe.routeWithArgs ,
                    arguments = SingleRecipe.arguments

                ) { navBackStackEntry ->
                    val recipe = navBackStackEntry.arguments?.getInt(SingleRecipe.recipeIdArg)?.let { recipeId ->
                        example.firstOrNull { it.id == recipeId }
                    } ?: Recipe(0, "Unknown Recipe", "No description available", 0)
                    SingleRecipeScreen(recipe)
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun RecipeFinderAppPreview() {
    RecipeFinderApp()
}