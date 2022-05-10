package com.example.naoandroidclient.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.naoandroidclient.ui.DetailViewModel
import com.example.naoandroidclient.ui.navigation.Screen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController, detailViewModel: DetailViewModel) {
    // todo move to view model
    val grouped = detailViewModel.apps.toList().filter { app -> app.name.contains(detailViewModel.searchText.value) }.groupBy { app -> app.name[0] }

    val focusManager = LocalFocusManager.current

    Column() {

        Row() {
            OutlinedTextField(
                value = detailViewModel.searchText.value,
                onValueChange = { searchText -> detailViewModel.searchText.value = searchText},
                label = { Text("search") },
                keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
                keyboardOptions = KeyboardOptions (
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                        )
            )
            Spacer(modifier = Modifier.height(8.dp))
        }


        LazyColumn {
            grouped.forEach{ (letter, apps) ->

                stickyHeader {
                    Row(
                        Modifier
                            .background(Color.Gray)
                            .fillMaxWidth()
                    ) {
                        Text(text = letter.toString(), fontSize = 30.sp)
                    }
                }

                items(apps) { app ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screen.DetailScreen.route + "/${app.id}")
                            }) {
                        Text(text = app.name, fontSize = 30.sp)
                        }

                }
            }

        }
    }
}