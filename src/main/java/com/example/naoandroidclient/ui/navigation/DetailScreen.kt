package com.example.naoandroidclient.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.naoandroidclient.ui.DetailViewModel

@Composable
fun DetailScreen(navController: NavController, detailViewModel: DetailViewModel, appId: Long?) {
    val app = detailViewModel.apps.find { it.id == appId }
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
                    detailViewModel.sendMessage("start_app", "${app?.let { app ->
                        detailViewModel.jsonifyApp(app) }}")
                }
            ) {
                Text(text = "start app")
            }
        }
    }
}
