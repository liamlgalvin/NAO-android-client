package com.example.naoandroidclient.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.naoandroidclient.ui.DetailViewModel

@Composable
fun HomeScreen(navController: NavController, detailViewModel: DetailViewModel) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column() {
            Text(text = "your robot's ip is ${detailViewModel.ip.value}")
            Text(text = "message is ${detailViewModel.message.value}")

            LazyColumn {
                items(detailViewModel.apps.toList()) { item ->
                    Button(
                        onClick =
                        {
                            navController.navigate(Screen.DetailScreen.route + "/${item.id}")
                        }
                    ) {
                        Text(text = "${item.name}")
                    }
                }
            }

        }
    }

}