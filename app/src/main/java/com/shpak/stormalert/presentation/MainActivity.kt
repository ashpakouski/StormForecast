package com.shpak.stormalert.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shpak.stormalert.domain.model.GeomagneticData
import com.shpak.stormalert.presentation.ui.theme.StormAlertTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: StormForecastViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.loadForecast()

        setContent {
//            val systemUiController = rememberSystemUiController()
//            if (isSystemInDarkTheme()) {
//                systemUiController.setSystemBarsColor(
//                    color = Color.Transparent
//                )
//            } else {
//                systemUiController.setSystemBarsColor(
//                    color = Color.White
//                )
//            }

            StormAlertTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopAppBar(
                                colors = TopAppBarDefaults.smallTopAppBarColors(
//                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                                    titleContentColor = MaterialTheme.colorScheme.primary,
                                ),
                                title = {
                                    Text("Storm Alert")
                                }
                            )
                        },
                    ) { innerPadding ->
                        if (viewModel.state.isLoading) {
                            Box(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .fillMaxSize()
                            ) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        } else {
                            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                                viewModel.state.forecast?.forecast?.let {
                                    items(it) { gmd ->
                                        ForecastCard(
                                            GeomagneticData(
                                                date = gmd.date,
                                                kpValue = gmd.kpValue
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastCard(
    geomagneticData: GeomagneticData
) {
    Card(
        shape = RoundedCornerShape(10.dp, 10.dp, 5.dp, 5.dp),
        // backgroundColor = MaterialTheme.colors.surface,
        // elevation = 3.dp,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),

        ) {
        Box(contentAlignment = Alignment.CenterStart) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = geomagneticData.date.toString(),
                    modifier = Modifier.padding(8.dp, 16.dp)
                )
                Text(
                    text = geomagneticData.kpValue.toString(),
                    modifier = Modifier.padding(8.dp, 16.dp)
                )
            }
        }
    }
}