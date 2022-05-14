package com.example.naoandroidclient.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.naoandroidclient.ui.DetailViewModel
import com.example.naoandroidclient.ui.connect.ConnectScreen
import com.example.naoandroidclient.ui.detail.DetailScreen
import com.example.naoandroidclient.ui.home.HomeScreen
import com.example.naoandroidclient.ui.search.SearchScreen

@Composable
fun Navigation(navController: NavHostController, viewModel: DetailViewModel) {

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
            DetailScreen(detailViewModel = viewModel, backStackEntry.arguments?.getLong("appId"))
        }
        composable(route = Screen.SearchScreen.route){
            SearchScreen(navController = navController, detailViewModel = viewModel)
        }
    }
}