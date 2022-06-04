package com.example.naoandroidclient.ui.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.naoandroidclient.R
import com.example.naoandroidclient.domain.App
import com.example.naoandroidclient.ui.image.SmallLogoImage
import com.example.naoandroidclient.ui.navigation.Screen

@Composable
fun SearchScreen(navController: NavController,
                 searchViewModel: SearchViewModel,
                 focusManager: FocusManager = LocalFocusManager.current) {

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {

        SearchBar(searchViewModel, focusManager)

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        SearchResults(
            searchViewModel, navController
        )
    }
}

@Composable
fun SearchBar(searchViewModel: SearchViewModel, focusManager: FocusManager) {
    OutlinedTextField(
        textStyle = TextStyle(Color.White),
        value = searchViewModel.searchText.value,
        onValueChange = {
                searchText -> searchViewModel.searchText.value = searchText
        },
        label = {
            Row() {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "search",
                    tint = Color.White
                )
                Text(stringResource(id = R.string.search), color = Color.White)
            }
        },
        keyboardActions = KeyboardActions(onSearch = {
            focusManager.clearFocus()
        }),
        keyboardOptions = KeyboardOptions (
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        trailingIcon = {
            Icon(
                Icons.Default.Clear,
                contentDescription = stringResource(id = R.string.clear_input),
                modifier = Modifier
                    .clickable {
                        searchViewModel.searchText.value = ""
                    },
                tint = Color.White
            )
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun SearchResults(
    searchViewModel: SearchViewModel,
    navController: NavController
) {


    var visible by remember {
        mutableStateOf(false)
    }

    visible = when (searchViewModel.searchText.value) {
        "" -> false
        else -> true
    }

    AnimatedVisibility(visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        if (searchViewModel.searchText.value != "")
            AppLazyColumn(searchViewModel, navController)
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun AppLazyColumn(
    searchViewModel: SearchViewModel,
    navController: NavController
) {
    LazyColumn {
        searchViewModel.getAppsGroupedFiltered(searchViewModel.searchText.value)
            .forEach { (letter, apps) ->
                stickyHeader {
                    Row(
                        Modifier
                            .background(color = Color.DarkGray)
                        //, shape = RoundedCornerShape(20.dp))
                        //    .clip(RoundedCornerShape(20.dp))
                            .fillMaxWidth()
                    ) {

                       GroupTitle(letter.toString())

                    }
                }

                items(apps) { app ->
                            SearchAppCard(navController = navController, app = app)

                }
            }

    }
}


@Composable
fun SearchAppCard(app: App, navController: NavController) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 5.dp, horizontal = 1.dp)
        .background(
            color = MaterialTheme.colors.onPrimary.copy(alpha = 0.12f),
            shape = RoundedCornerShape(20.dp)
        )
        .clip(RoundedCornerShape(20.dp))
        .clickable {
            navController.navigate(Screen.DetailScreen.route + "/${app.id}")
        }
    ) {
        Column(
            modifier = Modifier.padding(end = 10.dp)
        ) {
            app.getImageBitmap()?.let { SmallLogoImage(it) }
        }
        Column() {
            androidx.compose.material.Text(
                text = app.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
private fun GroupTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colors.primary.copy(alpha = 0.08f),
        contentColor = MaterialTheme.colors.onPrimary,
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .border(
                2.dp, color = MaterialTheme.colors.onBackground.copy(alpha = 0.60f)
                , RoundedCornerShape(100.dp)
            )
    ) {
        androidx.compose.material.Text(
            text = text,
            style = MaterialTheme.typography.button,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
        )
    }
}