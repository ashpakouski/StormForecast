package com.shpak.stormalert.presentation.forecast

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.shpak.stormalert.R
import com.shpak.stormalert.domain.model.GeomagneticForecast
import com.shpak.stormalert.presentation.ui.theme.StormAlertTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastListUi(
    viewModel: StormForecastViewModel,
    onOpenSettings: () -> Unit
) {
    StormAlertTheme(dynamicColor = false) {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        title = {
                            Text(
                                stringResource(R.string.app_name),
                                fontWeight = FontWeight(500)
                            )
                        },
                        actions = {
                            IconButton(onClick = onOpenSettings) {
                                Icon(
                                    imageVector = Icons.Filled.Settings,
                                    contentDescription = stringResource(R.string.settings_title)
                                )
                            }
                        }
                    )
                },
            ) { innerPadding ->
                ForecastListScreen(viewModel, innerPadding)
            }
        }
    }
}

@Composable
private fun ForecastListScreen(
    viewModel: StormForecastViewModel,
    innerPadding: PaddingValues
) {
    Box(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        if (viewModel.state.isLoading) {
            ForecastLoadingState(modifier = Modifier.align(Alignment.Center))
        } else {
            if (!viewModel.state.isError) {
                val forecast = remember { viewModel.state.forecast }
                ForecastLoadingSucceededState(forecast)
            } else {
                ForecastLoadingFailedState(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
private fun ForecastLoadingState(modifier: Modifier) {
    CircularProgressIndicator(modifier = modifier)
}

@Composable
private fun ForecastLoadingSucceededState(forecast: GeomagneticForecast?) {
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        forecast?.kpMax24?.let {
            item {
                MaxKpCard(maxKp = it)
            }
        }

        forecast?.forecast?.let {
            groupedForecast(it)
        }

        item {
            Box(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun ForecastLoadingFailedState(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Rounded.ErrorOutline,
            contentDescription = "",
            modifier = Modifier.size(42.dp)
        )
        Text(
            stringResource(R.string.error_forecast_fetch_failed),
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(32.dp)
        )
    }
}