package com.example.naoandroidclient.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.naoandroidclient.R
import com.example.naoandroidclient.domain.App
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.component.ButtonText
import com.example.naoandroidclient.ui.home.SectionHeaderText
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
            AppDescriptionHeader(app = app)

            AppDetailBody(mainViewModel, app)
        }


    }
}

@Composable
private fun AppDetailBody(
    mainViewModel: MainViewModel,
    app: App?
) {
    if (app != null) {
        StartAppButton(mainViewModel, app)
        AppDescriptionText(app = app)
    }
}

@Composable
private fun StartAppButton(
    mainViewModel: MainViewModel,
    app: App?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick =
            {
                mainViewModel.sendMessage(
                    "start_app", "${
                        app?.let { app ->
                            app.id
                        }
                    }"
                )
            }
        ) {
            ButtonText(stringResource(id = R.string.start_app))
            Icon(
                Icons.Filled.PlayArrow,
                contentDescription = "play",
            )
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
            SectionHeaderText(app.name)
        }
    }
    Row(modifier = Modifier
        .fillMaxWidth(),
        //.padding(top = 20.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        if (app != null) {
            Text(
                text = app.description,
                style = MaterialTheme.typography.subtitle1,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }}

@Composable
private fun AppDescriptionHeader(
    app: App) {
    Box (Modifier.fillMaxWidth()){
        app?.getImageBitmap()?.let { HeaderLogoImage(bitmap = it, modifier= Modifier.align(Alignment.Center)) }
    }
}


