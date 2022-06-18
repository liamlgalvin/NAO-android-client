package com.example.naoandroidclient.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.naoandroidclient.R
import com.example.naoandroidclient.domain.App
import com.example.naoandroidclient.ui.image.LargeLogoImage
import com.example.naoandroidclient.ui.image.MediumLogoImage
import com.example.naoandroidclient.ui.navigation.Screen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {
    val apps = homeViewModel.getTopApps()
    val groupedApps = homeViewModel.getAllAppsGrouped()

    if (apps.isEmpty() && groupedApps.isEmpty()) {
        NoAppsToDisplay(homeViewModel.robotIp.value)
        return
    }

    DisplayHomeScreenItems(apps, groupedApps, navController, homeViewModel)
}

@Composable
fun DisplayHomeScreenItems(
    apps: List<App>,
    groupedApps: Map<Char, List<App>>,
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())) {

        if (apps.isNotEmpty()) {
            TopApps(apps, navController)
        }

        if (groupedApps.isNotEmpty()){
            DiscoverApps(groupedApps.keys.sorted(), groupedApps, homeViewModel, navController)
        }

    }}

@Composable
fun NoAppsToDisplay(robotIp: String) {
    Box(modifier = Modifier.fillMaxSize()) {
    Column(modifier = Modifier
        .align(alignment = Alignment.Center)) {

        Text(text = stringResource(id = R.string.no_apps_to_display) ,
            modifier = Modifier.padding(horizontal = 20.dp),
            style = MaterialTheme.typography.h4,
            fontWeight = Bold,
            color = Color.LightGray
        )
        Text(text = stringResource(id = R.string.add_app_endpoint, robotIp) ,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            style = MaterialTheme.typography.h5,
            color = Color.White
        )
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
            else -> MaterialTheme.colors.onPrimary.copy(alpha = 0.12f)
        },
        contentColor = when {
            selected -> MaterialTheme.colors.onPrimary
            else -> MaterialTheme.colors.onPrimary
        },
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .border(
                2.dp, color = when {
                    selected -> MaterialTheme.colors.onBackground.copy(alpha = 0.60f)
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
fun DiscoverApps(
    appGroups: List<Char>,
    groupedApps: Map<Char, List<App>>,
    homeViewModel: HomeViewModel,
    navController: NavController
) {

    Column (horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(10.dp)){

        SectionHeaderText( text =  stringResource(id = R.string.discover))

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
fun SectionHeaderText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.h4,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
        color = Color.White,
        fontWeight = FontWeight.Bold
    )
}


@Composable
fun AppCard(app: App, navController: NavController) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 5.dp, horizontal = 20.dp)
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
            app.getImageBitmap()?.let { MediumLogoImage(it) }
        }
        Column() {
            Text(
                text = app.name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                color = Color.White,
                fontWeight = Bold
            )

            Text(
                text = app.getShortDescription(),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                color = Color.Gray
            )


        }


    }
}


@Composable
fun TopApps(apps: List<App>, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)) {


        SectionHeaderText( text = stringResource(id = R.string.top_apps))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            apps.forEach {app ->
                app.getImageBitmap()?.let {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .clickable {
                                navController.navigate(Screen.DetailScreen.route + "/${app.id}")
                            }) {
                        LargeLogoImage(it)
                        Text(text = app.name, color = Color.Gray)
                    }
                }
            }
        }

    }
}

