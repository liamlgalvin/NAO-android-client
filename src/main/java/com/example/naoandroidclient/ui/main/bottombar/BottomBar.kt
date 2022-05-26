package com.example.naoandroidclient.ui.main

import androidx.compose.animation.*
import androidx.compose.material.Button
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import com.example.naoandroidclient.domain.RobotStatus
import com.example.naoandroidclient.ui.MainViewModel

@Composable
fun BottomBar(mainViewModel: MainViewModel) {

    val robotStatus by mainViewModel.robotStatus.observeAsState()
    var visible by remember {
        mutableStateOf(false)
    }

    visible = when (robotStatus) {
        RobotStatus.NO_APP_RUNNING -> false
        RobotStatus.APP_RUNNING -> true
        else -> false
    }

    println(visible)

    AnimatedVisibility(visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        BottomAppBar() {
            mainViewModel.currentApp.value?.let { Text(text = it.name) }
            Button(
                onClick =
                {
                    mainViewModel.sendMessage("cancel", "")
                }
            ) {
                androidx.compose.material.Text(text = "cancel app")
            }
        }
    }

}