package com.example.naoandroidclient.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.connect.ConnectScreen
import com.example.naoandroidclient.ui.detail.DetailScreen
import com.example.naoandroidclient.ui.home.HomeScreen
import com.example.naoandroidclient.ui.search.SearchScreen

@Composable
fun Navigation(navController: NavHostController, mainViewModel: MainViewModel) {

    NavHost(navController = navController, startDestination = Screen.ConnectScreen.route) {
        composable(route = Screen.ConnectScreen.route) {
            ConnectScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(route = "${Screen.DetailScreen.route}/{appId}",
            arguments = listOf(navArgument("appId") { type = NavType.LongType })
        ){backStackEntry ->
            backStackEntry.arguments?.getLong("appId")
                ?.let { appId -> DetailScreen(mainViewModel = mainViewModel, appId) }
        }
        composable(route = Screen.SearchScreen.route){
            SearchScreen(navController = navController, mainViewModel = mainViewModel)
        }
    }
}