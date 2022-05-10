package com.example.naoandroidclient.ui.main

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.example.naoandroidclient.ui.DetailViewModel
import com.example.naoandroidclient.ui.navigation.Navigation

@Composable
fun MainScreen (viewModel: DetailViewModel) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { MainAppBar(scaffoldState, scope) },
        drawerContent = { DrawerContent(viewModel) },
    ) {
        Navigation(navController, viewModel)
    }
}
