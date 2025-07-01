package com.example.recipefinder

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipefinder.data.Ingredient
import com.example.recipefinder.data.Recipe
import com.example.recipefinder.data.RecipeViewModel
import com.example.recipefinder.data.Step
import com.example.recipefinder.data.Tag
import com.example.recipefinder.data.Unit
import com.example.recipefinder.database.readRecipesFromDatabase
import com.example.recipefinder.database.updateRecipeInDatabase
import com.example.recipefinder.database.writeRecipeToDatabase
import com.example.recipefinder.ui.components.RecipeFinderTabRow
import com.example.recipefinder.ui.home.HomeScreen
import com.example.recipefinder.ui.market.MarketScreen
import com.example.recipefinder.ui.recipes.SingleRecipeScreen
import com.example.recipefinder.ui.saved.SavedScreen
import com.example.recipefinder.ui.search.SearchScreen
import com.example.recipefinder.ui.settings.SettingsScreen
import com.example.recipefinder.ui.theme.Primary
import com.example.recipefinder.ui.theme.RecipeFinderTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

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
        val db = Firebase.firestore
        enableEdgeToEdge()
        setContent {
            RecipeFinderApp(db)
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
    Recipe(5, "Example Recipe 5", "Description of Example Recipe 5", 40, tags = listOf(Tag.SOUP, Tag.LUNCH, Tag.PASTA, Tag.DAIRY_FREE, Tag.TOFU, Tag.GLUTEN_FREE), ingredients = listOf(
        Ingredient(1, "Ingredient 1", 2, Unit.CUP, note = "Example note for ingredient 1"),
        Ingredient(2, "Ingredient 2", 1, Unit.TABLESPOON),
        Ingredient(3, "Ingredient 3", 3, Unit.TEASPOON)
    ), steps = listOf(
        Step(0, "This would be step 1", "This is the first step of the recipe. This is long winded to show that it can handle much longer descriptions."),
        Step(1, "This would be step 2", "This is the second step of the recipe. It can also be long. For example, you might need to explain how to prepare the ingredients or how to cook them."),
        Step(2, "Step 3", "This is the third step of the recipe. It can also be long. For example, you might need to explain how to prepare the ingredients or how to cook them.")
    )),

)

val recipeViewModel = RecipeViewModel()
val recipes = recipeViewModel.recipes
private fun getDatabaseRecipes(foundRecipes: List<Recipe>) {
    recipeViewModel.setRecipes(foundRecipes)
}

fun fetchRecipes(database: FirebaseFirestore) {
    // Simulate fetching recipes from a database
    readRecipesFromDatabase(db = database,
        { foundRecipes ->
            getDatabaseRecipes(
                foundRecipes
            )
        },
        {
            // Handle error case, e.g., show a message to the user
            println("Error fetching recipes: $it")
        }
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun RecipeFinderApp(database: FirebaseFirestore) {
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
                        fetchRecipes(database)
                        // Navigate to the selected destination
                        navController.navigateSingleTopTo(destination.route)
                    },
                    currentScreen = currentScreen
                )
            },
            modifier = Modifier.fillMaxSize(),

            floatingActionButton = {
                if(navController.currentDestination?.route != Market.route) {
                    FloatingActionButton(
                        onClick = { navController.navigateSingleTopTo(Market.route) },
                        containerColor = Primary,
                        contentColor = Color.White,
                        modifier = Modifier
                            .padding(end = (LocalConfiguration.current.screenWidthDp.dp / 2 - 44.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add"
                        )
                    }
                }
            }
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
                    SavedScreen(recipes, onRecipeClick = {recipe ->
                        // Handle recipe click, navigate to a detailed view
                        navController.navigateSingleTopTo("${SingleRecipe.route}/${recipe.id}")
                    })
                }
                composable(route = Search.route) {
                    SearchScreen(recipes, onRecipeClick = {recipe ->
                    // Handle recipe click, navigate to a detailed view
                    navController.navigateSingleTopTo("${SingleRecipe.route}/${recipe.id}")
                    })
                }
                composable(route = Market.route) {
                    MarketScreen(recipes, onRecipeClick = { recipe ->
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
                        recipes.firstOrNull { it.id == recipeId }
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
    RecipeFinderApp(database = Firebase.firestore)
}