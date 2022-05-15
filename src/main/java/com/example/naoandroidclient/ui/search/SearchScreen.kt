package com.example.naoandroidclient.ui.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.navigation.Screen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(navController: NavController,
                 mainViewModel: MainViewModel,
                 focusManager: FocusManager = LocalFocusManager.current) {

    Column() {

        Row() {
            OutlinedTextField(
                value = mainViewModel.getSearchText(),
                onValueChange = { searchText -> mainViewModel.setSearchText(searchText)},
                label = { Text("search") },
                keyboardActions = KeyboardActions(onSearch = {
                    focusManager.clearFocus()
                }),
                keyboardOptions = KeyboardOptions (
                    autoCorrect = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                        )
            )
            Spacer(modifier = Modifier.height(8.dp))
        }


        LazyColumn {
            mainViewModel.getAppsGrouped().forEach{ (letter, apps) ->

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