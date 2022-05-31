package com.example.naoandroidclient.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.naoandroidclient.domain.App
import com.example.naoandroidclient.ui.image.LargeLogoImage
import com.example.naoandroidclient.ui.image.MediumLogoImage
import com.example.naoandroidclient.ui.navigation.Screen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val apps = homeViewModel.getTopApps()
    val groupedApps = homeViewModel.getAllAppsGrouped()

    println("groupedApps = ${groupedApps}")

    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())) {

        TopApps(apps, navController)

        if (groupedApps.isNotEmpty()){
            AllApps(groupedApps.keys.sorted(), groupedApps, homeViewModel, navController)
        }

    }

}

private val emptyTabIndicator: @Composable (List<TabPosition>) -> Unit = {}

@Composable
private fun ChoiceChipContent(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        color = when {
            selected -> MaterialTheme.colors.primary.copy(alpha = 0.08f)
            else -> MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
        },
        contentColor = when {
            selected -> MaterialTheme.colors.primary
            else -> MaterialTheme.colors.onSurface
        },
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .border(
                2.dp, color = when {
                    selected -> MaterialTheme.colors.primary
                    else -> Color.Transparent
                }, RoundedCornerShape(100.dp)
            )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun AllApps(
    appGroups: List<Char>,
    groupedApps: Map<Char, List<App>>,
    homeViewModel: HomeViewModel,
    navController: NavController
) {

    Column (horizontalAlignment = Alignment.CenterHorizontally){

        Text(
            text = "Discover",
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        ScrollableTabRow(
            selectedTabIndex = homeViewModel.tabIndex.value,
            divider = {},
            backgroundColor = Color.Transparent,
            indicator = emptyTabIndicator
        ) {
            appGroups.forEachIndexed { index, title ->
                val selected = homeViewModel.tabIndex.value == index

                Tab(
                    selected = selected,
                    onClick = {
                        homeViewModel.tabIndex.value = index
                    },
                ) {
                    ChoiceChipContent(
                        text = title.toString(),
                        selected = index == homeViewModel.tabIndex.value,
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 16.dp)
                    )
                }
            }
        }
        }

        groupedApps[appGroups.elementAt(homeViewModel.tabIndex.value)]?.forEach { app ->
            AppCard(app, navController)
        }
    }


@Composable
fun AppCard(app: App, navController: NavController) {
    Row(modifier = Modifier.fillMaxWidth().clickable {
        navController.navigate(Screen.DetailScreen.route + "/${app.id}")
    }
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
            app.getImageBitmap()?.let { MediumLogoImage(it) }
        }
        Column() {
            Text(
                text = app.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp)
            )

            Text(
                text = app.getShortDescription(),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp)
            )


        }


    }
}


@Composable
fun TopApps(apps: List<App>, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)) {


        Text(text = "top apps", style = MaterialTheme.typography.h3,  modifier = Modifier.padding(20.dp))

        LazyRow(
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            items(apps) {
                apps.forEach { app ->
                    app.getImageBitmap()?.let {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .clickable {
                                    navController.navigate(Screen.DetailScreen.route + "/${app.id}")
                                }) {
                            LargeLogoImage(it)
                            Text(text = app.name)
                        }
                    }
                }
            }

        }
    }}

