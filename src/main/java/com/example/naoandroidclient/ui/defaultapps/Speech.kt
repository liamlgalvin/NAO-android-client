package com.example.naoandroidclient.ui.defaultapps

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.naoandroidclient.R
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.component.ButtonText

@Composable
fun SpeakScreen(defaultAppViewModel: DefaultAppViewModel, mainViewModel: MainViewModel) {

    val focusManager = LocalFocusManager.current


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 50.dp)
    ) {

        val message by defaultAppViewModel.message.observeAsState()

        message?.let { value ->
            OutlinedTextField(
                textStyle = TextStyle(Color.White),
                value = value,
                onValueChange = { message -> defaultAppViewModel.message.value = message },
                label = {
                    Text(
                        stringResource(id = R.string.what_nao_say),
                        color = Color.White
                    )
                },
                keyboardActions = KeyboardActions(onGo = {
                    robotSpeak(focusManager,defaultAppViewModel,mainViewModel)
                }),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Go
                ),
                trailingIcon = {
                    Icon(
                        Icons.Default.Clear,
                        contentDescription = stringResource(id = R.string.clear_input),
                        modifier = Modifier
                            .clickable {
                                defaultAppViewModel.message.value = ""
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
                robotSpeak(focusManager,defaultAppViewModel,mainViewModel)
            }
        ) {
            ButtonText(
                text = stringResource(id = R.string.speak)
            )
        }
    }
}

fun robotSpeak(focusManager: FocusManager, defaultAppViewModel: DefaultAppViewModel, mainViewModel: MainViewModel ) {
    if (defaultAppViewModel.message.value.isNullOrEmpty()) return

    focusManager.clearFocus()
    defaultAppViewModel.message.value?.let {
           message -> mainViewModel.robotSay(message)
    }
    defaultAppViewModel.message.value = ""
}


