package com.example.naoandroidclient.ui.navigation

sealed class Screen(val route: String) {
    object ConnectScreen: Screen("connect_screen")
    object HomeScreen: Screen("home_screen")
    object DetailScreen: Screen("detail_screen")
    object SearchScreen: Screen("search_screen")
}
