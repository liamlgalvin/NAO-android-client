package com.example.naoandroidclient.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.naoandroidclient.ui.DetailViewModel

@Composable
fun Navigation(viewModel : DetailViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.ConnectScreen.route) {
        composable(route = Screen.ConnectScreen.route) {
            ConnectScreen(navController = navController, detailViewModel = viewModel)
        }
        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController = navController, detailViewModel = viewModel)
        }
        composable(route = "${Screen.DetailScreen.route}/{appId}",
            arguments = listOf(navArgument("appId") { type = NavType.LongType })
        ){backStackEntry ->
        DetailScreen(navController = navController, detailViewModel = viewModel, backStackEntry.arguments?.getLong("appId"))
        }
    }
}