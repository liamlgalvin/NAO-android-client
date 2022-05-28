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
import com.example.naoandroidclient.domain.ConnectionStatus
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.connect.ConnectViewModel
import com.example.naoandroidclient.ui.detail.DetailViewModel
import com.example.naoandroidclient.ui.home.HomeViewModel
import com.example.naoandroidclient.ui.main.topbar.MainAppBarViewModel
import com.example.naoandroidclient.ui.navigation.Navigation
import com.example.naoandroidclient.ui.search.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen (mainViewModel: MainViewModel,
                searchViewModel: SearchViewModel,
                connectViewModel: ConnectViewModel,
                mainAppBarViewModel: MainAppBarViewModel,
                homeViewModel: HomeViewModel,
                detailViewModel: DetailViewModel
) {

    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    val connectionStatus by mainViewModel.connectionStatus.observeAsState()

    val progressBar by mainViewModel.showProgressBar.observeAsState()


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { MainAppBar( mainAppBarViewModel = mainAppBarViewModel, scaffoldState = scaffoldState, scope = scope, navController = navController)
        },
        bottomBar = {BottomBar(mainViewModel = mainViewModel)},
        drawerContent = { DrawerContent(viewModel = mainViewModel) }) {


        when (connectionStatus) {
            ConnectionStatus.CONNECTED -> displaySnackbar(scope, scaffoldState, mainViewModel)
            ConnectionStatus.NOT_CONNECTED -> displaySnackbar(scope, scaffoldState, mainViewModel)
            else -> displaySnackbar(scope, scaffoldState, mainViewModel)
        }

        when (progressBar) {
            true -> LinearProgressIndicator(Modifier.fillMaxWidth())
        }

        Navigation( navController = navController,
            mainViewModel = mainViewModel,
            searchViewModel = searchViewModel,
            connectViewModel = connectViewModel,
            homeViewModel = homeViewModel,
            detailViewModel = detailViewModel
        )

    }
}

fun displaySnackbar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    mainViewModel: MainViewModel
) {
    if (mainViewModel.connectedState.value.toString().isEmpty() || mainViewModel.connectedState.value == null) return

    if (mainViewModel.previousConnectedState.value != mainViewModel.connectedState.value.toString()) {
        scope.launch {
            mainViewModel.getConnectedState()?.let {
                scaffoldState.snackbarHostState.showSnackbar(
                    message = it,
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

}
