package com.example.naoandroidclient.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.naoandroidclient.R
import com.example.naoandroidclient.domain.ConnectionStatus
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.component.AppTextLogo

@Composable
fun DrawerContent(viewModel: MainViewModel) {
    // todo
    //val menuItems = viewModel.getMenuItems

    val connectionStatus by viewModel.connectionStatus.observeAsState()

        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AppTextLogo(color = Color.DarkGray)

            if(connectionStatus == ConnectionStatus.CONNECTED)
                ConnectedMenu(viewModel = viewModel)
            else
                DisconnectedMenu()
        }
}

@Composable
fun DisconnectedMenu() {
    About()
}

@Composable
fun ConnectedMenu(viewModel: MainViewModel) {
    Disconnect(viewModel)
    About()
}

@Composable
fun Disconnect(viewModel: MainViewModel) {
    Row(horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                viewModel.disconnectWebSocket()
            }) {
        Icon(Icons.Default.ExitToApp, null)
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = stringResource(id = R.string.disconnect), style = MaterialTheme.typography.h4)
    }
}

@Composable
fun About() {
    Row(horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
            }) {
        Icon(Icons.Default.Accessibility, null)
        Spacer(modifier = Modifier.padding(10.dp))
        Text(text = stringResource(id = R.string.about), style = MaterialTheme.typography.h4)
    }
}


