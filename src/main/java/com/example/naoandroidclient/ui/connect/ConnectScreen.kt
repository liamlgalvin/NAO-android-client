package com.example.naoandroidclient.ui.connect

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.naoandroidclient.R
import com.example.naoandroidclient.domain.ConnectionStatus
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.navigation.Screen

@Composable
fun ConnectScreen(navController: NavController, mainViewModel: MainViewModel, connectViewModel: ConnectViewModel) {

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
            .fillMaxHeight()
            .padding(horizontal = 50.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {

        OutlinedTextField(
            value = connectViewModel.ip.value,
            onValueChange = { ip -> connectViewModel.setIp(ip)  },
            label = { Text(stringResource(id = R.string.nao_robot_ip_address)) },
            keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Go
            ),
            trailingIcon = {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = stringResource(id = R.string.clear_input),
                    modifier = Modifier
                        .clickable {
                            connectViewModel.ip.value = ""
                        }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Button(
            modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
            onClick =
            {
                focusManager.clearFocus()
                connectViewModel.ip.value = "192.168.1.125" //todo remove
                if (connectViewModel.isValidIp(connectViewModel.ip.value)) {
                    mainViewModel.toggleProgressBar()
                    mainViewModel.createRobotMessageService(connectViewModel.ip.value)
                    mainViewModel.sendCreateNotification()
                    mainViewModel.sendObserveNotification()
                }
            }
        ) {
            Text(text = stringResource(id = R.string.connect))
        }
    }
}
