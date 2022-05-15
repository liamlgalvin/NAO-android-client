package com.example.naoandroidclient.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.naoandroidclient.ui.MainViewModel

@Composable
fun DetailScreen(mainViewModel: MainViewModel, appId: Long) {

    val app = mainViewModel.getAppById(appId)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column() {
            Text(text = "app Id =  ${app?.id}")
            Text(text = "app name =  ${app?.name}")
            Text(text = "app description =  ${app?.description}")
            Button(
                onClick =
                {
                    mainViewModel.sendMessage("start_app", "${app?.let { app ->
                        mainViewModel.jsonifyApp(app) }}")
                }
            ) {
                Text(text = "start app")
            }
        }
    }
}