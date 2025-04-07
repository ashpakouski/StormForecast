package com.shpak.stormalert.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shpak.stormalert.presentation.main.MainBottomBarNavigationContainer
import com.shpak.stormalert.presentation.navigation.Route
import com.shpak.stormalert.presentation.settings.SettingsScreen
import com.shpak.stormalert.presentation.ui.theme.StormForecastTheme

@Composable
fun ForecastApp() {
    StormForecastTheme {
        val navController = rememberNavController() // TODO Create custom controller

        NavHost(
            navController = navController,
            startDestination = Route.MAIN
        ) {
            composable(Route.MAIN) {
                MainBottomBarNavigationContainer(
                    onNavigateToSettings = {
                        navController.navigate(Route.SETTINGS)
                    }
                )
            }

            composable(Route.SETTINGS) {
                SettingsScreen(onNavigateBack = navController::popBackStack)
            }
        }
    }
}