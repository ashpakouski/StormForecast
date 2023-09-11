package com.shpak.stormalert.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                                    itemsIndexed(it) { i, gmd ->
                                        ForecastCard(
                                            GeomagneticData(
                                                date = gmd.date,
                                                kpValue = gmd.kpValue
                                            ),
                                            isFirst = i == 0,
                                            isLast = i == it.size - 1
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