package com.example.naoandroidclient.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.sp
import com.example.naoandroidclient.domain.ConnectionStatus
import com.example.naoandroidclient.ui.DetailViewModel
import com.example.naoandroidclient.ui.navigation.Screen

@Composable
fun DrawerContent(viewModel: DetailViewModel) {
    // todo
    //val menuItems = viewModel.getMenuItems

    val connectionStatus by viewModel.connectionStatus.observeAsState()

    Column(Modifier.fillMaxWidth(),
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


