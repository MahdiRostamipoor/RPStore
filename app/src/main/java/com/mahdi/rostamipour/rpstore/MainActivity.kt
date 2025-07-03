package com.mahdi.rostamipour.rpstore

import android.R.attr.type
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mahdi.rostamipour.rpstore.pages.CartScreen
import com.mahdi.rostamipour.rpstore.pages.CategoriesScreen
import com.mahdi.rostamipour.rpstore.pages.HomeScreen
import com.mahdi.rostamipour.rpstore.pages.ProfileScreen
import com.mahdi.rostamipour.rpstore.ui.theme.RPStoreTheme

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
fun Greeting() {
    val navController = rememberNavController()
    val destinations = listOf(
        NavItem("Home", Icons.Default.Home, "HomeScreen"),
        NavItem("Cart", Icons.Default.ShoppingCart, "CartScreen"),
        NavItem("Profile", Icons.Default.Person, "ProfileScreen")
    )
    var selectedDestination = rememberSaveable { mutableStateOf(destinations.first().route) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("RPStore") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle icon click */ }) {
                        Icon(Icons.Default.Home, contentDescription = "App Icon")
                    }
                }
            )
        },
        bottomBar = {
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
                ProfileScreen()
            }

            composable(
                route = "CategoriesScreen/{categoryId}",
                arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
            ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getInt("categoryId")
                // Your CategoriesScreen composable here
                CategoriesScreen(categoryId = categoryId?:0)
            }
        }
    }
}

data class NavItem(val label: String, val icon: ImageVector, val route: String)
