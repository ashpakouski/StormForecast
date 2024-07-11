package com.shpak.stormalert.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shpak.stormalert.presentation.forecast.ForecastListScreen
import com.shpak.stormalert.presentation.settings.SettingsScreen
import com.shpak.stormalert.presentation.ui.theme.StormAlertTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {

        // FIXME: Temporary implementation
        object Route {
            const val FORECAST_LIST = "forecast_list"
            const val SETTINGS = "settings"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            StormAlertTheme(dynamicColor = false) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Route.FORECAST_LIST
                    ) {
                        composable(Route.FORECAST_LIST) {
                            ForecastListScreen(onOpenSettings = {
                                navController.navigate(Route.SETTINGS)
                            })
                        }

                        composable(Route.SETTINGS) {
                            SettingsScreen(onNavigateBack = navController::popBackStack)
                        }
                    }
                }
            }
        }
    }
}