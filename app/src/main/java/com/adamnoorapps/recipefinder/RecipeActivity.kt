package com.adamnoorapps.recipefinder

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.adamnoorapps.recipefinder.data.Ingredient
import com.adamnoorapps.recipefinder.data.Recipe
import com.adamnoorapps.recipefinder.data.RecipeViewModel
import com.adamnoorapps.recipefinder.data.Step
import com.adamnoorapps.recipefinder.data.Tag
import com.adamnoorapps.recipefinder.data.Unit
import com.adamnoorapps.recipefinder.database.readRecipesFromDatabase
import androidx.compose.runtime.collectAsState
import com.adamnoorapps.recipefinder.database.readSavedRecipesFromDatabase
import com.adamnoorapps.recipefinder.database.writeRecipeToDatabase
import com.adamnoorapps.recipefinder.ui.components.RecipeFinderTabRow
import com.adamnoorapps.recipefinder.ui.createrecipe.CreateRecipeScreen
import com.adamnoorapps.recipefinder.ui.home.HomeScreen
import com.adamnoorapps.recipefinder.ui.login.AccountServiceImpl
import com.adamnoorapps.recipefinder.ui.market.MarketScreen
import com.adamnoorapps.recipefinder.ui.recipes.SingleRecipeScreen
import com.adamnoorapps.recipefinder.ui.saved.SavedScreen
import com.adamnoorapps.recipefinder.ui.search.SearchScreen
import com.adamnoorapps.recipefinder.ui.settings.SettingsScreen
import com.adamnoorapps.recipefinder.ui.theme.Primary
import com.adamnoorapps.recipefinder.ui.theme.RecipeFinderTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
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
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = Firebase.auth
        val db = Firebase.firestore
        enableEdgeToEdge()
        setContent {
            RecipeFinderApp(db, auth)
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
private fun getDatabaseRecipes(foundRecipes: List<Recipe>, savedRecipes: List<Recipe>) {
    recipeViewModel.setRecipes(foundRecipes)
    recipeViewModel.setSavedRecipes(savedRecipes)
}

fun fetchRecipes(database: FirebaseFirestore) {
    // Simulate fetching recipes from a database
    readRecipesFromDatabase(db = database,
        { foundRecipes ->
            readSavedRecipesFromDatabase(database, foundRecipes, Firebase.auth.currentUser?.uid.toString()) { savedRecipes ->
                // Update the ViewModel with the fetched recipes
                getDatabaseRecipes(foundRecipes, savedRecipes)
            }
        },
        {
            // Handle error case, e.g., show a message to the user
            println("Error fetching recipes: $it")
        }
    )
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun RecipeFinderApp(database: FirebaseFirestore, auth: FirebaseAuth?) {
    val recipes = recipeViewModel.recipes.collectAsState()
    val savedRecipes = recipeViewModel.savedRecipes.collectAsState()
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
                if(auth != null && auth.currentUser != null) {
                    RecipeFinderTabRow(
                        allScreens = recipeDestinations,
                        onTabSelected = { destination ->
                            fetchRecipes(database)
                            // Navigate to the selected destination
                            navController.navigate(destination.route)
                        },
                        currentScreen = currentScreen
                    )
                }
            },
            modifier = Modifier.fillMaxSize(),

            floatingActionButton = {
                if(auth != null && auth.currentUser != null)
                {
                    if(currentBackstack.value?.destination?.route != CreateRecipe.route) {
                        FloatingActionButton(
                            onClick = { navController.navigateSingleTopTo(CreateRecipe.route) },
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
            }
        )
        { innerPadding ->
            // Main content of the app goes here
            NavHost(
                navController = navController,
                startDestination = Home.route,
                Modifier.padding(innerPadding)) {
                composable(route = Home.route) {
                    if (auth != null) {
                        HomeScreen(
                            auth,
                            {navController.navigateSingleTopTo(Market.route)},
                            {navController.navigateSingleTopTo(Saved.route)},
                            {
                                navController.popBackStack()
                                navController.navigateSingleTopTo(Home.route)
                            }
                        )
                    }
                }
                composable(route = Saved.route) {
                    SavedScreen(savedRecipes.value, onRecipeClick = {recipe ->
                        // Handle recipe click, navigate to a detailed view
                        navController.navigate("${SingleRecipe.route}/${recipe.id}")
                    })
                }
                composable(route = Search.route) {
                    SearchScreen(recipes.value, onRecipeClick = {recipe ->
                    // Handle recipe click, navigate to a detailed view
                    navController.navigate("${SingleRecipe.route}/${recipe.id}")
                    })
                }
                composable(route = Market.route) {
                    MarketScreen(recipes.value, onRecipeClick = { recipe ->
                        // Handle recipe click, navigate to a detailed view
                        navController.navigate("${SingleRecipe.route}/${recipe.id}")
                    })
                }
                composable(route = Settings.route) {
                    SettingsScreen(
                        signOut = {
                            AccountServiceImpl().signOut()
                            navController.popBackStack(navController.graph.startDestinationId, false)
                            navController.navigateSingleTopTo(Home.route)
                        }
                    )
                }
                composable(route = CreateRecipe.route) {
                    CreateRecipeScreen(
                        onCreate = { recipe ->
                            // Handle recipe creation, e.g., save to database
                            writeRecipeToDatabase(database, recipe)
                            // Navigate to the newly created recipe's detail screen
                            navController.navigate("${SingleRecipe.route}/${recipe.id}")
                        }

                    )
                }
                composable(
                    route = SingleRecipe.routeWithArgs ,
                    arguments = SingleRecipe.arguments

                ) {
                    val recipeId = it.arguments?.getInt(SingleRecipe.recipeIdArg)
                    val recipe = recipes.value.find { it.id == recipeId }
                    recipe?.let {
                        SingleRecipeScreen(recipe = it, model = recipeViewModel)
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun RecipeFinderAppPreview() {
    RecipeFinderApp(database = Firebase.firestore, null)
}