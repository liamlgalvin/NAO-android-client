package com.example.naoandroidclient.ui.main.topbar

import android.annotation.SuppressLint
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.naoandroidclient.ui.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MainAppBar(
    mainAppBarViewModel: MainAppBarViewModel,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    navController: NavHostController
) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    TopAppBar(
       elevation = 0.dp,
        backgroundColor = Color.Transparent,//MaterialTheme.colorScheme.background,
        title = {},
        navigationIcon =  {
            NavigationIcon(mainAppBarViewModel = mainAppBarViewModel, navController = navController, currentBackStackEntry = currentBackStackEntry)
                          },
        actions =  {

             SearchIcon(mainAppBarViewModel = mainAppBarViewModel, navController = navController, currentBackStackEntry = currentBackStackEntry)

             TopBarAction(imageVector = Icons.Default.Menu, onClick = {
                 scope.launch { scaffoldState.drawerState.open() }
             }, description = "menu")
         }
    )
}



@Composable
private fun TopBarAction(
    onClick: () -> Unit,
    imageVector: ImageVector,
    description: String
) {
    IconButton(onClick = onClick) {
        Icon(imageVector = imageVector, contentDescription = description, tint = Color.White)
    }
}

@Composable
fun NavigationIcon(mainAppBarViewModel: MainAppBarViewModel, navController: NavHostController, currentBackStackEntry: NavBackStackEntry?) {

    if (currentBackStackEntry?.destination?.route?.let { mainAppBarViewModel.showNavigationIcon(it) } == false) return

    return IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "back", tint = Color.White)
    }
}

@Composable
fun SearchIcon(mainAppBarViewModel: MainAppBarViewModel, navController: NavHostController, currentBackStackEntry: NavBackStackEntry?) {

    if (currentBackStackEntry?.destination?.route?.let { mainAppBarViewModel.showSearchIcon(it) } == false) return

    return IconButton(onClick = { navController.navigate(Screen.SearchScreen.route)}) {
        Icon(Icons.Default.Search, contentDescription = "search", tint = Color.White)
    }
}

