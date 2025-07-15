package com.mahdi.rostamipour.rpstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mahdi.rostamipour.rpstore.intent.FavoriteIntent
import com.mahdi.rostamipour.rpstore.model.FavoriteModel
import com.mahdi.rostamipour.rpstore.model.ProductsModel
import com.mahdi.rostamipour.rpstore.pages.CartScreen
import com.mahdi.rostamipour.rpstore.pages.CategoriesScreen
import com.mahdi.rostamipour.rpstore.pages.FavoriteScreen
import com.mahdi.rostamipour.rpstore.pages.FilterScreen
import com.mahdi.rostamipour.rpstore.pages.HomeScreen
import com.mahdi.rostamipour.rpstore.pages.ProductScreen
import com.mahdi.rostamipour.rpstore.pages.ProfileScreen
import com.mahdi.rostamipour.rpstore.ui.theme.RPStoreTheme
import com.mahdi.rostamipour.rpstore.viewModel.FavoriteViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RPStoreTheme {
                Greeting()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun Greeting(favoriteViewModel: FavoriteViewModel = koinViewModel()) {
    val navController = rememberNavController()
    val destinations = listOf(
        NavItem("Home", Icons.Default.Home, "HomeScreen"),
        NavItem("Cart", Icons.Default.ShoppingCart, "CartScreen"),
        NavItem("Profile", Icons.Default.Person, "ProfileScreen")
    )

    val selectedDestination = rememberSaveable { mutableStateOf(destinations.first().route) }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination?.route


    val shouldShowBottomBar = destinations.any { it.route == currentDestination }

    Scaffold(
        topBar = {
            if (!shouldShowBottomBar && currentBackStackEntry?.destination?.route != "ProductScreen") {
                TopAppBar(
                    title = { Text(getLabelPage(currentBackStackEntry?.destination?.route ?:"",navController)) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "back")
                        }
                    }
                )
            }else if (!shouldShowBottomBar && currentBackStackEntry?.destination?.route == "ProductScreen"){

                val productModel = navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<ProductsModel>("product")

                val state by favoriteViewModel.favoriteProductsState.collectAsState()

                LaunchedEffect(productModel?.id ?: 0) {
                    favoriteViewModel.handleIntent(FavoriteIntent.CheckFavoriteStatus(productModel?.id ?:0))
                }

                val isFavorite = state.favoriteStatus[productModel?.id ?:0] == true


                TopAppBar(title = {
                    Row (Modifier.fillMaxWidth().fillMaxHeight(), horizontalArrangement = Arrangement.SpaceBetween
                        , verticalAlignment = Alignment.CenterVertically){
                        Text(text =  productModel?.title ?: "" , maxLines = 1 , overflow = TextOverflow.Ellipsis
                            , modifier = Modifier.weight(1f).padding(8.dp).wrapContentWidth(
                                Alignment.Start))

                        Spacer(Modifier.padding(12.dp))

                        IconButton(
                            onClick = {

                                if (isFavorite) {
                                    favoriteViewModel.handleIntent(FavoriteIntent.DeleteFavoriteProduct(
                                        productModel?.id ?: 0
                                    ))
                                } else {
                                    val favModel = FavoriteModel(productModel?.category ?: 0,
                                        productModel?.description ?: "",productModel?.id ?: 0,
                                        productModel?.offer ?: false,productModel?.picture?: "",
                                        productModel?.price?: 0,productModel?.priceOffer?: 0,
                                        productModel?.title ?: "")
                                    favoriteViewModel.handleIntent(FavoriteIntent.AddFavoriteProduct(favModel))
                                }


                                favoriteViewModel.handleIntent(FavoriteIntent.CheckFavoriteStatus(productModel?.id ?:0)) },
                            modifier = Modifier.padding(8.dp).wrapContentWidth(Alignment.End)
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (isFavorite) Color.Red else Color.Gray
                            )
                        }

                    }
                })


            }
        },
        bottomBar = {
            if (shouldShowBottomBar) {
                NavigationBar {
                    destinations.forEach { item ->
                        NavigationBarItem(
                            selected = selectedDestination.value == item.route,
                            onClick = {
                                selectedDestination.value = item.route
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "HomeScreen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("HomeScreen") {
                HomeScreen(navigation = navController)
            }
            composable("CartScreen") {
                CartScreen()
            }
            composable("ProfileScreen") {
                ProfileScreen(navigation = navController)
            }

            composable(
                route = "CategoriesScreen/{categoryId}",
                arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
            ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getInt("categoryId")
                CategoriesScreen(categoryId = categoryId?:0,  navigation = navController)
            }

            composable("FilterScreen") {
                FilterScreen(navigation = navController)
            }

            composable("ProductScreen") {
                val model = navController.previousBackStackEntry?.savedStateHandle?.get<ProductsModel>("product")
                ProductScreen(model)
            }

            composable("FavoriteScreen") {
                FavoriteScreen(navigation = navController)
            }

        }
    }
}

fun getLabelPage(rote : String , navigation : NavHostController) : String{
    val screenTitles = mapOf(
        "HomeScreen" to "Home",
        "CartScreen" to "Cart",
        "ProfileScreen" to "Profile",
        "FilterScreen" to "Filtering",
        "CategoriesScreen" to "Category",
        "ProductScreen" to "Product",
        "FavoriteScreen" to "Your favorites"
    )

    val currentRoute = navigation.currentBackStackEntry?.destination?.route


    val baseRoute = currentRoute?.substringBefore("/") ?: currentRoute

    val screenTitle = screenTitles[baseRoute] ?: ""

    return screenTitle
}

data class NavItem(val label: String, val icon: ImageVector, val route: String)
