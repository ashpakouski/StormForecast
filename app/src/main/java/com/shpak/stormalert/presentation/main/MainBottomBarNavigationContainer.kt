package com.shpak.stormalert.presentation.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shpak.stormalert.R
import com.shpak.stormalert.presentation.forecast.ForecastListScreen
import com.shpak.stormalert.presentation.map.MapScreen

@Composable
fun MainBottomBarNavigationContainer(
    onNavigateToSettings: () -> Unit
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                BottomNavigationBar(navController)
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavigationSections.FORECAST_MEDIUM_RANGE.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(BottomNavigationSections.FORECAST_MEDIUM_RANGE.route) {
                ForecastListScreen(onOpenSettings = onNavigateToSettings)
            }

            composable(BottomNavigationSections.FORECAST_REAL_TIME.route) {
                MapScreen()
            }
        }
    }
}

private enum class BottomNavigationSections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    FORECAST_MEDIUM_RANGE(
        R.string.screen_forecast_medium_range_title,
        Icons.Filled.AccessTime,
        "forecast_medium_range"
    ),
    FORECAST_REAL_TIME(
        R.string.screen_forecast_real_time_title,
        Icons.Filled.Map,
        "forecast_real_time"
    )
}

@Composable
private fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        BottomNavigationSections.entries.forEach { item ->
            NavigationBarItem(
                selected = item.route == currentRoute,
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.title)
                    )
                },
                onClick = {
                    navController.navigate(item.route)
                },
            )
        }
    }
}