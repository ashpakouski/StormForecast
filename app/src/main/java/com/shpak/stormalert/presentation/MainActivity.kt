package com.shpak.stormalert.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Storm
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shpak.stormalert.presentation.forecast.ForecastListScreen
import com.shpak.stormalert.presentation.map.MapScreen
import com.shpak.stormalert.presentation.settings.SettingsScreen
import com.shpak.stormalert.presentation.ui.theme.StormAlertTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    companion object {
//
//        // FIXME: Temp
//        object Route {
//            const val FORECAST_LIST = "forecast_list"
//            const val SETTINGS = "settings"
//            const val MAP = "map"
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "start"
            ) {
                composable(
                    route = "start"
                ) { backStackEntry ->
                    val nestedNavController = rememberNavController()
                    val navBackStackEntry by nestedNavController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    val routes = listOf("tab_1", "tab_2")

                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .safeContentPadding(),
                        bottomBar = {
                            Row {
                                routes.forEach {
                                    Button(onClick = {
                                        nestedNavController.navigate(it)
                                    }) {
                                        Text(
                                            text = "$it ($currentRoute)"
                                        )
                                    }
                                }
                            }
                        }
                    ) {
                        NavHost(
                            navController = nestedNavController,
                            startDestination = "tab_1",
                            modifier = Modifier.padding(it)
                        ) {
                            composable("tab_1") {
                                Box(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = "Tab 1",
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .clickable {
                                                navController.navigate("settings")
                                            }
                                    )
                                }
                            }

                            composable("tab_2") {
                                Box(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = "Tab 2",
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                }

                composable(
                    route = "settings"
                ) { _ ->
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Settings",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        enableEdgeToEdge()
//
//        setContent {
//            val navController = rememberNavController()
//            val currentBackStackEntry by navController.currentBackStackEntryAsState()
//            val currentDestination = currentBackStackEntry?.destination?.route
//
//            StormAlertTheme {
//                Scaffold(
//                    bottomBar = {
//                        Log.d("TAG123", "Dest: $currentDestination")
//                        if (currentDestination in listOf(Route.FORECAST_LIST, Route.MAP)) {
//                            BottomNavigationBar(navController)
//                        }
//                    }
//                ) {
//                    NavHost(
//                        navController = navController,
//                        startDestination = Route.FORECAST_LIST,
//                        modifier = Modifier.padding(it)
//                    ) {
//                        composable(Route.FORECAST_LIST) {
//                            ForecastListScreen(onOpenSettings = {
//                                navController.navigate(Route.SETTINGS)
//                            })
//                        }
//
//                        composable(Route.SETTINGS) {
//                            SettingsScreen(onNavigateBack = navController::popBackStack)
//                        }
//
//                        composable(Route.MAP) {
//                            MapScreen()
//                        }
//                    }
//                }
//            }
//        }
//    }
    }
}

//@Composable
//fun BottomBar(navController: NavHostController) {
//    val screens = listOf(
//        "forecast",
//        "map"
//    )
//
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentDestination = navBackStackEntry?.destination
//
//    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
//    if (bottomBarDestination) {
//        BottomNavigation {
//            screens.forEach { screen ->
//                AddItem(
//                    screen = screen,
//                    currentDestination = currentDestination,
//                    navController = navController
//                )
//            }
//        }
//    }
//}

//@Composable
//fun RootNavigationGraph(navController: NavHostController) {
//    NavHost(
//        navController = navController,
//        route = "root",
//        startDestination = ""
//    ) {
//        authNavGraph(navController = navController)
//        composable(route = Graph.HOME) {
//            HomeScreen()
//        }
//    }
//}

//@Composable
//private fun BottomNavigationBar(
//    navController: NavHostController
//) {
//    NavigationBar {
//        NavigationBarItem(
//            icon = { Icon(Icons.Default.Storm, contentDescription = null) },
//            label = { Text("3 Days") },
//            selected = navController.currentDestination?.route == "forecast_list",
//            onClick = { navController.navigate("forecast_list") }
//        )
//        NavigationBarItem(
//            icon = { Icon(Icons.Default.Radar, contentDescription = null) },
//            label = { Text("Now") },
//            selected = navController.currentDestination?.route == "map",
//            onClick = { navController.navigate("map") }
//        )
//    }
//}