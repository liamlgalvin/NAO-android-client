package com.example.naoandroidclient.ui.connect

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.naoandroidclient.domain.ConnectionStatus
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.navigation.Screen

@Composable
fun ConnectScreen(navController: NavController, mainViewModel: MainViewModel) {

    val focusManager = LocalFocusManager.current

    val connectionStatus by mainViewModel.connectionStatus.observeAsState()
    if (connectionStatus == ConnectionStatus.CONNECTED) {
        navController.navigate(Screen.HomeScreen.route)
        // return to avoid displaying the screen for a split second if user presses back button
        return
    }



    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {

        OutlinedTextField(
            value = mainViewModel.ip.value,
            onValueChange = { ip -> mainViewModel.setIp(ip)  },
            label = { Text("NAO Robot IP address") },
            keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Go
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick =
            {
                focusManager.clearFocus()
                mainViewModel.ip.value = "192.168.1.125" //todo remove
                if (mainViewModel.isValidIp(mainViewModel.ip.value)) {
                    mainViewModel.toggleProgressBar()
                    mainViewModel.createRobotMessageService()
                    mainViewModel.sendObserveNotification()
                }
            }
        ) {
            Text(text = "Connect")
        }
    }
}
