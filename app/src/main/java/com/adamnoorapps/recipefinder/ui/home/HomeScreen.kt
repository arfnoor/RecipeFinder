package com.adamnoorapps.recipefinder.ui.home


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.adamnoorapps.recipefinder.ui.theme.RecipeFinderTheme
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.unit.sp
import com.adamnoorapps.recipefinder.ui.login.LoginModal
import com.adamnoorapps.recipefinder.ui.theme.Primary
import com.adamnoorapps.recipefinder.ui.theme.Secondary
import com.adamnoorapps.recipefinder.ui.theme.Tertiary
import com.google.firebase.auth.FirebaseAuth


@Composable
fun HomeScreen(
    auth : FirebaseAuth,
    marketOnClick : () -> Unit,
    recipeOnClick : () -> Unit,
    refresh : () -> Unit
)
{
    if(auth.currentUser == null)
    {
        LoginModal({ refresh() })
    }
    else
    {
        val scrollState = rememberScrollState()
        LaunchedEffect(scrollState.value) {
            if (scrollState.value > 10) { // threshold
                recipeOnClick()
                kotlinx.coroutines.delay(20) // wait for the page swap to finish
                scrollState.scrollTo(0)
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),

            ) {
            Column (
                modifier = Modifier.verticalScroll(scrollState)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4F) // Purple 500
                        .background(Primary) // Purple 500
                )
                {
                    Column (
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ){
                        Spacer(
                            modifier = Modifier.height(50.dp)
                        )
                        Text(
                            text = "Recipedia",
                            modifier = Modifier
                                .background(Primary) // Purple 500
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                shadow = Shadow(
                                    color = Secondary,
                                    offset = Offset(0.0f, 0.0f),
                                    blurRadius = 15f,

                                    )
                            ),
                            textAlign = TextAlign.Center,
                            color = androidx.compose.ui.graphics.Color.White,
                        )
                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )
                        Text(
                            text = "Cook. Bake. Create.",
                            modifier = Modifier
                                .background(Primary) // Purple 500
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.titleLarge.copy(
                                shadow = Shadow(
                                    color = Secondary,
                                    offset = Offset(0.0f, 0.0f),
                                    blurRadius = 15f,

                                    )
                            ),
                            textAlign = TextAlign.Center,
                            color = androidx.compose.ui.graphics.Color.White,
                        )
                        Spacer(
                            modifier = Modifier.height(50.dp)
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            ElevatedButton (
                                onClick = { recipeOnClick() },
                                shape = MaterialTheme.shapes.extraSmall,
                                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                    containerColor = Secondary
                                )
                            ) {
                                Text(
                                    text = "Saved Recipes",
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 8.dp)
                                )
                            }

                            ElevatedButton (
                                onClick = { marketOnClick() },
                                shape = MaterialTheme.shapes.extraSmall,
                                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                    containerColor = Secondary
                                ),
                            ) {
                                Text(
                                    text = "Find Recipes",
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(0.dp, 8.dp, 0.dp, 8.dp)
                                )
                            }
                        }
                        Spacer(
                            modifier = Modifier.height(25.dp)
                        )
                    }

                }
                Text(
                    text = "Discover a world of culinary delights with Recipedia, your ultimate recipe companion. Discover, save, create recipes of all kinds. Find your next meal with what's around the house, or recreate your own masterpiece with our easy-to-use recipe storage. Whether you're a seasoned chef or a beginner in the kitchen, Recipedia has something for every user. \n\nChoose an option above to begin your culinary journey, or swipe up to quickly navigate to your saved recipes",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Secondary,
                    textAlign = TextAlign.Center,
                )
                Spacer(
                    modifier = Modifier.height(50.dp)
                )
                Text(
                    text = "Welcome to Recipedia",
                    modifier = Modifier
                        .padding(16.dp, bottom = 0.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Primary,
                    textAlign = TextAlign.Center,
                )
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.KeyboardArrowDown,
                    contentDescription = "Favorite Icon",
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    tint = Tertiary
                )
                Spacer(
                    modifier = Modifier.height(1000.dp)
                )
            }




        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    RecipeFinderTheme {
        HomeScreen(FirebaseAuth.getInstance(), {}, {}, {})
    }
}