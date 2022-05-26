package com.example.naoandroidclient.ui.main.topbar

import androidx.lifecycle.ViewModel
import com.example.naoandroidclient.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainAppBarViewModel @Inject constructor() : ViewModel()  {

    private val pagesToShowSearch = listOf(Screen.HomeScreen.route, Screen.DetailScreen.route)
    private val pagesToShowNavigation = listOf(Screen.DetailScreen.route, Screen.SearchScreen.route)


    fun showSearchIcon(currentRoute: String) : Boolean {
        pagesToShowSearch.forEach { page ->
            if (currentRoute.contains(page, ignoreCase = true)) return true
        }
        return false
    }

    fun showNavigationIcon(currentRoute: String) : Boolean {
        pagesToShowNavigation.forEach { page ->
            if (currentRoute.contains(page, ignoreCase = true)) return true
        }
        return false
    }

}