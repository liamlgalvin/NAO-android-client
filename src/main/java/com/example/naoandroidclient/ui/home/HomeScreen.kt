package com.example.naoandroidclient.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.naoandroidclient.ui.MainViewModel
import com.example.naoandroidclient.ui.navigation.Screen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController, mainViewModel: MainViewModel) {

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
