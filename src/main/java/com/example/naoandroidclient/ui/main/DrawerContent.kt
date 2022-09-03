package com.example.naoandroidclient.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.navigation.NavController
import com.example.naoandroidclient.R
import com.example.naoandroidclient.domain.ConnectionStatus
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.component.AppTextLogo
import com.example.naoandroidclient.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(viewModel: MainViewModel, navController: NavController) {

    val connectionStatus by viewModel.connectionStatus.observeAsState()
    val battery by viewModel.battery.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        DrawerContents(connectionStatus, viewModel, Modifier.align(Alignment.TopCenter), battery, navController)
    }
}

@Composable
private fun DrawerContents(
    connectionStatus: ConnectionStatus?,
    viewModel: MainViewModel,
    modifier: Modifier,
    battery: Int?,
    navController: NavController
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        AppTextLogo(color = Color.DarkGray, modifier = Modifier.padding(top = 20.dp, bottom = 5.dp))
        RobotInfo(connectionStatus, battery, viewModel.robotIp.value)
        DrawerMenu(connectionStatus, viewModel, navController)
    }
}

@Composable
fun RobotInfo(connectionStatus: ConnectionStatus?, battery: Int?, ip: String) {
    if (connectionStatus != ConnectionStatus.CONNECTED) return

    Row(horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
           ) {

            Column() {
                IpInfo(ip)
                BatteryInfo(battery)
            }

    }

    Divider(color = Color.LightGray, thickness = 1.dp)
}

@Composable
fun BatteryInfo(battery: Int?) {
    battery?.let { stringResource(id = R.string.battery, it) }?.let { BatteryText(it, battery) }
}

@Composable
fun IpInfo(ip: String) {
    RobotInfoText("Ip: $ip")
}

@Composable
private fun DrawerMenu(
    connectionStatus: ConnectionStatus?,
    viewModel: MainViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        if (connectionStatus == ConnectionStatus.CONNECTED)
            ConnectedMenu(viewModel = viewModel, navController)
        else
            DisconnectedMenu()
    }
}

@Composable
fun DisconnectedMenu() {
    //About()
}

@Composable
fun ConnectedMenu(viewModel: MainViewModel, navController: NavController) {
    //Walk()
    //Speak(navController)
    Spacer(modifier = Modifier.padding(10.dp))
    Disconnect(viewModel)
    //About()
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
fun Walk() {
    Row(horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
            }) {
        Icon(Icons.Filled.Directions, null)
        Spacer(modifier = Modifier.padding(10.dp))
        DrawerMenuText(text = "Walk") // todo: make string
    }
}

@Composable
fun Speak(navController: NavController) {
    Row(horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screen.SpeakScreen.route)
            }) {
        Icon(Icons.Filled.ChatBubble, null)
        Spacer(modifier = Modifier.padding(10.dp))
        DrawerMenuText(text = stringResource(id = R.string.speak))
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

@Composable
fun RobotInfoText(text: String, color: Color) {
    Text(
        text = text,
        style = MaterialTheme.typography.subtitle2,
        modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
        color = color,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun RobotInfoText(text: String) {
    RobotInfoText(text, Color.DarkGray)
}

@Composable
fun BatteryText(text: String, battery: Int?) {
    RobotInfoText(text = text, getColour(battery))
}


fun getColour(battery: Int?): Color {
    return when (battery) {
        in 0 .. 20 -> Color.Red
        else -> Color.DarkGray
    }
}


