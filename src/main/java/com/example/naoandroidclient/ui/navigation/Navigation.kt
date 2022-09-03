package com.example.naoandroidclient.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.connect.ConnectScreen
import com.example.naoandroidclient.ui.connect.ConnectViewModel
import com.example.naoandroidclient.ui.defaultapps.DefaultAppViewModel
import com.example.naoandroidclient.ui.defaultapps.RestScreen
import com.example.naoandroidclient.ui.defaultapps.SpeakScreen
import com.example.naoandroidclient.ui.defaultapps.WalkScreen
import com.example.naoandroidclient.ui.detail.DetailScreen
import com.example.naoandroidclient.ui.detail.DetailViewModel
import com.example.naoandroidclient.ui.home.HomeScreen
import com.example.naoandroidclient.ui.home.HomeViewModel
import com.example.naoandroidclient.ui.search.SearchScreen
import com.example.naoandroidclient.ui.search.SearchViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    searchViewModel: SearchViewModel,
    connectViewModel: ConnectViewModel,
    homeViewModel: HomeViewModel,
    detailViewModel: DetailViewModel,
    defaultAppViewModel: DefaultAppViewModel
) {

    NavHost(navController = navController, startDestination = Screen.ConnectScreen.route) {
        composable(route = Screen.ConnectScreen.route) {
            ConnectScreen(navController = navController, mainViewModel = mainViewModel, connectViewModel = connectViewModel)
        }
        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController = navController, homeViewModel = homeViewModel)
        }
        composable(route = "${Screen.DetailScreen.route}/{appId}",
            arguments = listOf(navArgument("appId") { type = NavType.LongType })
        ){backStackEntry ->
            backStackEntry.arguments?.getLong("appId")
                ?.let { appId -> DetailScreen(detailViewModel = detailViewModel, mainViewModel = mainViewModel, appId) }
        }
        composable(route = Screen.SearchScreen.route){
            SearchScreen(navController = navController, searchViewModel = searchViewModel)
        }


        composable(route = Screen.SpeakScreen.route){
            SpeakScreen(defaultAppViewModel, mainViewModel)
        }
        composable(route = Screen.WalkScreen.route){
            WalkScreen(defaultAppViewModel, mainViewModel)
        }
        composable(route = Screen.RestScreen.route){
            RestScreen(defaultAppViewModel, mainViewModel)
        }

    }
}