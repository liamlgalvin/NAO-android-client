package com.example.naoandroidclient.ui.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.naoandroidclient.ui.DetailViewModel
import com.example.naoandroidclient.ui.navigation.Navigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen (viewModel: DetailViewModel) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    val connectionStatus by viewModel.connectionStatus.observeAsState()
    val progressBar by viewModel.showProgressBar.observeAsState()


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { MainAppBar( viewModel = viewModel, scaffoldState = scaffoldState, scope = scope, navController = navController)
        },
        drawerContent = { DrawerContent(viewModel = viewModel) }) {


        when (connectionStatus) {
            else -> displaySnackbar(scope, scaffoldState, viewModel)
        }


        Navigation(navController, viewModel)


        when (progressBar) {
            true -> LinearProgressIndicator(Modifier.fillMaxWidth())
            //else -> LinearProgressIndicator(Modifier.fillMaxWidth())
        }

    }
}

fun displaySnackbar(scope: CoroutineScope, scaffoldState: ScaffoldState, viewModel: DetailViewModel ) {
    if (viewModel.connectedState.value.toString().isEmpty()) return

    scope.launch {
        scaffoldState.snackbarHostState.showSnackbar(
            message = viewModel.connectedState.value.toString(),
            duration = SnackbarDuration.Short
        ) }
}
