package com.example.naoandroidclient.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.naoandroidclient.domain.App
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.component.ButtonText
import com.example.naoandroidclient.ui.image.HeaderLogoImage

@Composable
fun DetailScreen(detailViewModel: DetailViewModel, mainViewModel: MainViewModel, appId: Long) {

    val app = detailViewModel.getAppById(appId)

    val message by mainViewModel.message.observeAsState() // todo: his will be removed

    AppDetail(app, detailViewModel, mainViewModel) // todo: dont pass main view model


}

@Composable
fun AppDetail(app: App?, detailViewModel: DetailViewModel, mainViewModel: MainViewModel) {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp)) {

        if (app != null) {
            AppDescriptionHeader(app = app) { mainViewModel.sendMessage("start_app", "${app.id}") }

            AppDescriptionText(app = app)
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center) {
            Button(
                onClick =
                {
                    mainViewModel.sendMessage(
                        "start_app", "${
                            app?.let { app -> app.id
                            }
                        }"
                    )
                }
            ) {
                ButtonText("Start App")        }
        }





    }
}

@Composable
fun AppDescriptionText(app: App) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp),
        horizontalArrangement = Arrangement.Start) {
        if (app != null) {
            Text(text = app.name, style = MaterialTheme.typography.h3, color = MaterialTheme.colors.primary)

        }
    }
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 20.dp),
        horizontalArrangement = Arrangement.Start,) {
        if (app != null) {
            Text(text = app.description, style = MaterialTheme.typography.subtitle1, color = MaterialTheme.colors.primaryVariant)
        }
    }}

@Composable
private fun AppDescriptionHeader(
    app: App,
    onClick: () -> Unit
) {
    Box {
        app?.getImageBitmap()?.let { HeaderLogoImage(bitmap = it) }
        PlayButton(
            modifier = Modifier
                .padding(end = 20.dp)
                .size(100.dp)
                .align(Alignment.BottomEnd)
                .offset(y = 40.dp), // overlap bottom of image
            onClick = onClick
        )
    }
}

@Composable
fun PlayButton(
    modifier: Modifier = Modifier,
    outlineSize: Dp = 3.dp,
    outlineColor: Color = MaterialTheme.colors.surface,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        LargeFloatingActionButton(
            modifier = Modifier
                .padding(outlineSize)
                .fillMaxSize(),
            onClick = onClick
        ){
            Row(verticalAlignment = Alignment.CenterVertically){
                // Text(text = "start app", modifier = Modifier.padding(start= 15.dp))
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = "Localized description",
                    modifier = Modifier.size(FloatingActionButtonDefaults.LargeIconSize),
                )
            }
        }
    }
}

