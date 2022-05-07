package com.example.naoandroidclient.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.naoandroidclient.domain.ConnectionStatus
import com.example.naoandroidclient.ui.DetailViewModel

@Composable
fun ConnectScreen(navController: NavController, detailViewModel: DetailViewModel) {
    val connectionStatus by detailViewModel.connectionStatus.observeAsState()
    if (connectionStatus == ConnectionStatus.CONNECTED) {

        navController.navigate(Screen.HomeScreen.route)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Text(detailViewModel.connectedState.value)
        Spacer(modifier = Modifier.height(8.dp))
        Text("ip = ${detailViewModel.ip.value}" )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = detailViewModel.ip.value,
            onValueChange = { ip -> detailViewModel.setIp(ip)  },
            label = { Text("NAO Robot IP address") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick =
            {
                detailViewModel.connectedState.value = "connecting..." //todo remove
                detailViewModel.ip.value = "192.168.1.125" //todo remove
                if (detailViewModel.isValidIp()) {
                    detailViewModel.createRobotMessageService()
                    detailViewModel.sendObserveNotification()
                }
            }
        ) {
            Text(text = "Connect")
        }
    }
}
