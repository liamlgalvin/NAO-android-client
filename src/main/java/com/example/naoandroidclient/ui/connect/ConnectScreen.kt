package com.example.naoandroidclient.ui.connect

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.naoandroidclient.R
import com.example.naoandroidclient.domain.ConnectionStatus
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.component.AppTextLogo
import com.example.naoandroidclient.ui.component.ButtonText
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
       // verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()

            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp)
        ) {
            AppTextLogo(Modifier.align(Alignment.Center), color = Color.LightGray)
        }

    }

    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 50.dp)) {

        val ip by connectViewModel.ip.observeAsState()

        ip?.let { value ->
            OutlinedTextField(
                textStyle = TextStyle(Color.White),
                value = value,
                onValueChange = { ip -> connectViewModel.ip.value = ip },
                label = { Text(stringResource(id = R.string.nao_robot_ip_address), color = Color.White) },
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
                            },
                        tint = Color.White
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            onClick =
            {
                focusManager.clearFocus()
                if (connectViewModel.ip.value?.let { connectViewModel.isValidIp(it) } == true) {
                    mainViewModel.toggleProgressBar()
                    mainViewModel.createRobotMessageService(connectViewModel.ip.value!!)
                    mainViewModel.sendCreateConnectionNotification()
                    mainViewModel.sendObserveNotification()
                }
            }
        ) {
            ButtonText(
                text = stringResource(id = R.string.connect)
            )
        }
    }
}
