package com.example.naoandroidclient.ui.defaultapps

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.component.Controller

@Composable
fun WalkScreen(defaultAppViewModel: DefaultAppViewModel, mainViewModel: MainViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Controller {
            mainViewModel.moveRobot(it)
        }
    }

}