package com.example.naoandroidclient.ui.main

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.naoandroidclient.domain.RobotStatus
import com.example.naoandroidclient.ui.MainViewModel

@Composable
fun BottomBar(mainViewModel: MainViewModel) {

    val robotStatus by mainViewModel.robotStatus.observeAsState()
    var visible by remember {
        mutableStateOf(false)
    }

    visible = when (robotStatus) {
        RobotStatus.NO_APP_RUNNING -> false
        RobotStatus.APP_RUNNING -> true
        else -> false
    }

    AnimatedVisibility(visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        BottomAppBar(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier.align(Alignment.CenterStart),
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Clear,
                            contentDescription = "",
                            Modifier.height(40.dp)
                                .width(40.dp)
                                .padding(horizontal = 5.dp)
                        )


                        mainViewModel.currentApp.value?.let { Text(text = it.name) }
                    }
                }
                Column(
                    modifier = Modifier.align(Alignment.CenterEnd),

                    ) {
                    Button(
                        onClick =
                        {
                            mainViewModel.sendMessage("cancel", "")

                        }
                    ) {
                        androidx.compose.material.Text(text = "cancel app")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ComposablePreview() {
    BottomAppBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterStart),
                ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Clear,
                        contentDescription = "",
                    Modifier.height(40.dp)
                        .width(40.dp)
                        .padding(horizontal = 5.dp)
                    )


                    Text(text =  "preview")
                }
            }
            Column(
                modifier = Modifier.align(Alignment.CenterEnd),

                ) {
                Button(
                    onClick =
                    {
                    }
                ) {
                    androidx.compose.material.Text(text = "cancel app")
                }
            }
        }
    }
}
