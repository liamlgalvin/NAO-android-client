package com.example.naoandroidclient.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.naoandroidclient.ui.DetailViewModel

@Composable
fun MainScreen(navController: NavController, detailViewModel: DetailViewModel) {

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ){
        OutlinedTextField(
            value = detailViewModel.ip.value,
            onValueChange = { detailViewModel.ip.value = it },
            label = { Text("NAO Robot IP address") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,

            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick =
            {
                if (detailViewModel.isValidIp(detailViewModel.ip.value))
                    navController.navigate(Screen.DetailScreen.route)
            },
            Modifier.align(Alignment.End)
            ) {
            Text(text = "Connect")
            }
    }
}

