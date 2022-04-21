package com.example.naoandroidclient.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.naoandroidclient.ui.DetailViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel = DetailViewModel()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController, detailViewModel = viewModel)
        }
        composable(route = Screen.DetailScreen.route,
        ){
                DetailScreen(detailViewModel = viewModel)
        }
    }
}