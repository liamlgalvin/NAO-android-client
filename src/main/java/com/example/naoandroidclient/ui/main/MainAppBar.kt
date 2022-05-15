package com.example.naoandroidclient.ui.main

import android.annotation.SuppressLint
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.naoandroidclient.R
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainAppBar(
    viewModel: MainViewModel,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    navController: NavHostController
) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    TopAppBar(
        title = {
                Text(text = stringResource(id = R.string.app_name))
            },
        navigationIcon =  {
            NavigationIcon(navController = navController, currentBackStackEntry = currentBackStackEntry)
        },
     actions =  {

         SearchIcon(navController = navController, currentBackStackEntry = currentBackStackEntry)

         TopBarAction(imageVector = Icons.Default.Menu, onClick = {
             scope.launch { scaffoldState.drawerState.open() }
         }, description = "menu")
         }
    )
}



@Composable
private fun TopBarAction( //todo change this
    onClick: () -> Unit,
    imageVector: ImageVector,
    description: String
) {
    IconButton(onClick = onClick) {
        Icon(imageVector = imageVector, contentDescription = description)
    }
}

@Composable
fun NavigationIcon(navController: NavHostController, currentBackStackEntry: NavBackStackEntry?) {

    if (currentBackStackEntry?.destination?.route?.let { showNavigationIcon(it) } == false) return

    return IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "back")
    }
}

@Composable
fun SearchIcon(navController: NavHostController, currentBackStackEntry: NavBackStackEntry?) {

    if (currentBackStackEntry?.destination?.route?.let { showSearchIcon(it) } == false) return

    return IconButton(onClick = { navController.navigate(Screen.SearchScreen.route)}) {
        Icon(Icons.Default.Search, contentDescription = "back")
    }
}

fun showSearchIcon(currentRoute: String) : Boolean {
    // move to viewModel
    var pagesToShowSearch = listOf<String>(Screen.HomeScreen.route, Screen.DetailScreen.route)

    pagesToShowSearch.forEach { page ->
        if (currentRoute.contains(page, ignoreCase = true)) return true
    }
    return false
}

fun showNavigationIcon(currentRoute: String) : Boolean {
    // move to viewModel
    var pagesToShowNavigation = listOf<String>(Screen.DetailScreen.route,Screen.SearchScreen.route)

    pagesToShowNavigation.forEach { page ->
        if (currentRoute.contains(page, ignoreCase = true)) return true
    }
    return false
}
