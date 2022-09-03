package com.example.naoandroidclient.ui.defaultapps


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.naoandroidclient.R
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.component.ButtonText

@Composable
fun RestScreen(defaultAppViewModel: DefaultAppViewModel, mainViewModel: MainViewModel) {


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 50.dp)
    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            onClick =
            {
                mainViewModel.robotRest()
            }
        ) {
            ButtonText(
                text =  stringResource(id = R.string.rest)
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            onClick =
            {
                mainViewModel.robotStand()
            }
        ) {
            ButtonText(
                text = stringResource(id = R.string.stand)
            )
        }
    }
}



