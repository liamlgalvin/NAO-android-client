package com.example.naoandroidclient.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.naoandroidclient.domain.ConnectionStatus
import com.example.naoandroidclient.ui.DetailViewModel
import com.example.naoandroidclient.ui.navigation.Screen

@Composable
fun DrawerContent(viewModel: DetailViewModel) {
    // todo
    //val menuItems = viewModel.getMenuItems

    val connectionStatus by viewModel.connectionStatus.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(connectionStatus == ConnectionStatus.CONNECTED)
            ConnectedMenu(viewModel = viewModel)
        else
            DisconnectedMenu(viewModel = viewModel)
    }
}

@Composable
fun DisconnectedMenu(viewModel: DetailViewModel) {
    Row(Modifier
        .fillMaxWidth()
        .clickable {
        }) {
        Text(text = "about", fontSize = 30.sp)
    }

}

@Composable
fun ConnectedMenu(viewModel: DetailViewModel) {
    Row(Modifier
        .fillMaxWidth()
        .clickable {
            viewModel.disconnectWebSocket()
        }) {
        Text(text = "disconnect", fontSize = 30.sp)
    }
    Row(Modifier
        .fillMaxWidth()
        .clickable {
        }) {
        Text(text = "about", fontSize = 30.sp)
    }
}



