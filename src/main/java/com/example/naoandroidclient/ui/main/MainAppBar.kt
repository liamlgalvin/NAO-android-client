package com.example.naoandroidclient.ui.main

import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.naoandroidclient.R
import com.example.naoandroidclient.ui.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainAppBar(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    TopAppBar(
        title = {
                Text(text = stringResource(id = R.string.app_name))
            },
        actions = {
//            TopBarAction(imageVector = Icons.Default.Search, onClick = {
//                // TODO
//            }, description = "search")
            TopBarAction(imageVector = Icons.Default.Menu, onClick = {
                scope.launch { scaffoldState.drawerState.open() }
            }, description = "menu")
        }
    )
}



@Composable
private fun TopBarAction(
    onClick: () -> Unit,
    imageVector: ImageVector,
    description: String
) {
    IconButton(onClick = onClick) {
        Icon(imageVector = imageVector, contentDescription = description)
    }
}
