package com.example.naoandroidclient.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.naoandroidclient.R
import com.example.naoandroidclient.domain.ConnectionStatus
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.component.AppTextLogo

@Composable
fun DrawerContent(viewModel: MainViewModel) {

    val connectionStatus by viewModel.connectionStatus.observeAsState()

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        DrawerContents(connectionStatus, viewModel, Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun DrawerContents(
    connectionStatus: ConnectionStatus?,
    viewModel: MainViewModel,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        AppTextLogo(color = Color.DarkGray, modifier = Modifier.padding(vertical = 20.dp))

        DrawerMenu(connectionStatus, viewModel)
    }
}

@Composable
private fun DrawerMenu(
    connectionStatus: ConnectionStatus?,
    viewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (connectionStatus == ConnectionStatus.CONNECTED)
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
        Icon(Icons.Filled.Logout
            , null)
        Spacer(modifier = Modifier.padding(10.dp))
        DrawerMenuText(text = stringResource(id = R.string.disconnect))
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
        Icon(Icons.Filled.Info, null)
        Spacer(modifier = Modifier.padding(10.dp))
        DrawerMenuText(text = stringResource(id = R.string.about))
    }
}

@Composable
fun DrawerMenuText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
        color = Color.DarkGray,
        fontWeight = FontWeight.Bold
    )
}


