package com.example.naoandroidclient.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.naoandroidclient.ui.DetailViewModel

@Composable
fun DetailScreen(detailViewModel: DetailViewModel) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column() {
            Text(text = "your robot's ip is ${detailViewModel.ip.value}")
        }

    }

}